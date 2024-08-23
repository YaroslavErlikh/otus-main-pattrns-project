package erlikh.yaroslav.editor.config

import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.common.rest.RestHelper
import erlikh.yaroslav.editor.security.EditorAuthenticationManager
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport

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
    SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityProblemSupport problemSupport, EditorAuthenticationManager authenticationManager) {
        http
                .csrf { it.disable() }
                .cors { it.disable() }
                .authorizeHttpRequests { auth ->
                    auth.requestMatchers(RestHelper.AUTH_WHITELIST).anonymous()
                            .requestMatchers("/api/v1/admin/**").hasAuthority(UserRole.ADMIN.name())
                            .anyRequest().authenticated()
                }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .exceptionHandling { exceptionHandlingConfigurer ->
//                    exceptionHandlingConfigurer.authenticationEntryPoint(problemSupport)
//                    exceptionHandlingConfigurer.accessDeniedHandler(problemSupport)
                    exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> request.sendError(401, /*"Unauthorized"*/ authException.getMessage() + "\nCause:\n" + authException.getCause().getMessage() + "\nSuppressed:\n" + authException.getSuppressed()))
                }
                .oauth2ResourceServer { oauth2 ->
                    oauth2.jwt { jwtConfigurer ->
                        jwtConfigurer.authenticationManager(authenticationManager)
                    }
                }
                .build()
    }
}