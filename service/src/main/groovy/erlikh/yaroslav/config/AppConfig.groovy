package erlikh.yaroslav.config

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.openapi.InfoOpenApiCustomizer
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

    @Bean
    OpenApiCustomizer infoOpenApiCustomizer() {
        return InfoOpenApiCustomizer as OpenApiCustomizer
    }
}
