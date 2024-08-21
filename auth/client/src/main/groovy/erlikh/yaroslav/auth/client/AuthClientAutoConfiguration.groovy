package erlikh.yaroslav.auth.client

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestInterceptor

@AutoConfiguration
@EnableConfigurationProperties(AuthClientProps.class)
class AuthClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

    @Bean
    AuthClient authClient(
            RestTemplateBuilder restTemplateBuilder,
            AuthClientProps authClientProps
    ) {
        return new AuthClient(RestTemplateFactory.create(restTemplateBuilder, authClientProps, new ClientHttpRequestInterceptor[0]))
    }
}
