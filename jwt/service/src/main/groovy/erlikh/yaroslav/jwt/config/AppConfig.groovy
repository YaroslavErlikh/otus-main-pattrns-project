package erlikh.yaroslav.jwt.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import erlikh.yaroslav.common.openapi.InfoOpenApiCustomizer
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.commons.core.serialization.ObjectMapperHelperKt

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
    }

    @Bean
    OpenApiCustomizer infoOpenApiCustomizer() {
        return new InfoOpenApiCustomizer()
    }
}
