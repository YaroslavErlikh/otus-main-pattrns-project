package erlikh.yaroslav.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.auth.service.AuthUserDetailsService
import erlikh.yaroslav.common.openapi.InfoOpenApiCustomizer
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder() as PasswordEncoder
    }

    @Bean
    OpenApiCustomizer infoOpenApiCustomizer() {
        return new InfoOpenApiCustomizer() as OpenApiCustomizer
    }

    @Bean
    ProviderManager authenticationManager(PasswordEncoder passwordEncoder, AuthUserDetailsService userDetailsService) {
        return new ProviderManager(new DaoAuthenticationProvider(passwordEncoder).setUserDetailsService(userDetailsService))
    }
}