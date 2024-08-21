package erlikh.yaroslav.jwt.client

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import ru.sokomishalov.commons.core.serialization.ObjectMapperHelperKt

@AutoConfiguration
@EnableConfigurationProperties(JwtClientProps.class)
class JwtClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return ObjectMapperHelperKt.OBJECT_MAPPER
    }

    @Bean
    JwtClient jwtClient(
            RestTemplateBuilder restTemplateBuilder,
            JwtClientProps jwtClientProps
    ) {
        return new JwtClient(RestTemplateFactory.create(restTemplateBuilder, jwtClientProps))
    }
}
