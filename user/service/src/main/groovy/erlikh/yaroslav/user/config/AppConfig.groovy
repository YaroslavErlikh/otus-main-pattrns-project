package erlikh.yaroslav.user.config

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.openapi.InfoOpenApiCustomizer
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

    @Bean
    OpenApiCustomizer infoOpenApiCustomizer(){
        return new InfoOpenApiCustomizer()
    }
}
