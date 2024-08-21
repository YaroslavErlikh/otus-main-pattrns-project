package erlikh.yaroslav.jwt.config

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.openapi.InfoOpenApiCustomizer
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.commons.core.serialization.ObjectMapperHelperKt

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    ObjectMapper objectMapper() {
        return ObjectMapperHelperKt.OBJECT_MAPPER
    }

    @Bean
    OpenApiCustomizer infoOpenApiCustomizer() {
        return new InfoOpenApiCustomizer()
    }
}
