package erlikh.yaroslav.config

import erlikh.yaroslav.common.rest.RestHelper
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport

import static erlikh.yaroslav.common.rest.RestHelper.*

@EnableWebSecurity
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Import(SecurityProblemSupport.class)
@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityProblemSupport problemSupport,
            AuthenticationManager authenticationManager
    ) {
        http
                .csrf { it.disable() }
                .cors { it.disable() }
                .authorizeHttpRequests { auth ->
                    auth.requestMatchers(AUTH_WHITELIST).anonymous()
                            .anyRequest().authenticated()
                }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .exceptionHandling { configurer ->
                    configurer.authenticationEntryPoint(problemSupport)
                    configurer.accessDeniedHandler(problemSupport)
                }
                .oauth2ResourceServer { oauth2 ->
                    oauth2.jwt { configurer ->
                        configurer.authenticationManager(authenticationManager)
                    }
                }
                .build()
    }
}