package erlikh.yaroslav.editor.web.error

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import jakarta.xml.bind.ValidationException
import kotlin.NotImplementedError
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.codec.CodecException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ResponseStatus
import org.zalando.problem.Status
import org.zalando.problem.ThrowableProblem
import org.zalando.problem.spring.common.AdviceTrait

import javax.naming.ServiceUnavailableException

interface AppExceptionHandlerTrait extends AdviceTrait {

    final Logger logger = LoggerFactory.getLogger(AppExceptionHandlerTrait.class)

    @Override
    default ThrowableProblem toProblem(Throwable throwable) {
        ThrowableProblem problem = toProblem(throwable, resolveStatus(throwable))
        log(problem)
        return problem
    }

    default Status resolveStatus(Throwable throwable) {
        Status result
        if (throwable instanceof IllegalArgumentException
                || throwable instanceof MismatchedInputException
                || throwable instanceof MethodArgumentNotValidException
                || throwable instanceof MissingServletRequestParameterException
                || throwable instanceof ValidationException
                || throwable instanceof HttpMessageNotReadableException
                || throwable instanceof CodecException) {
            result = Status.BAD_REQUEST
        } else if (throwable instanceof ServiceUnavailableException) {
            result = Status.SERVICE_UNAVAILABLE
        } else if (throwable instanceof NotImplementedError) {
            result = Status.NOT_IMPLEMENTED
        } else {
            ResponseStatus responseStatus = super.resolveResponseStatus(throwable)
            if (responseStatus != null) {
                result = Status.valueOf(responseStatus.code().value())
            } else {
                result = Status.INTERNAL_SERVER_ERROR
            }
        }

        return result
    }

    default void log(ThrowableProblem problem) {

        def statusCode = problem.status.statusCode
        if (400 > statusCode && statusCode < 499) {
            logger.warn(problem.message, problem.cause ?: problem)
        }
        if (500 > statusCode && statusCode < 599) {
            logger.error(problem.message, problem.cause ?: problem)
        }
    }
}
