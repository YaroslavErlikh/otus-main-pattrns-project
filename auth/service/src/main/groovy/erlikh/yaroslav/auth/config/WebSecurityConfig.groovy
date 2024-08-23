package erlikh.yaroslav.auth.config

import erlikh.yaroslav.common.rest.RestHelper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf { it.disable() }
                .cors { it.disable() }
                .authorizeHttpRequests { auth ->
                    auth
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers(RestHelper.AUTH_WHITELIST).permitAll()
                            .anyRequest().authenticated()
                }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .exceptionHandling { ex ->
                    ex.authenticationEntryPoint((request, response, authException) ->
                            response.sendError(401, "Unauthorized")
                    )
                }
                .build()
    }
}