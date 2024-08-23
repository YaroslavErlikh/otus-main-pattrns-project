package erlikh.yaroslav.jwt.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
    }

    @Bean
    JwtClient jwtClient(
            RestTemplateBuilder restTemplateBuilder,
            JwtClientProps jwtClientProps
    ) {
        return new JwtClient(RestTemplateFactory.create(restTemplateBuilder, jwtClientProps))
    }
}
