package erlikh.yaroslav.user.client

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.rest.RestTemplateFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean

@AutoConfiguration
@EnableConfigurationProperties(UserClientProps.class)
class UserClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

    @Bean
    UserClient userClient(
            RestTemplateBuilder restTemplateBuilder,
            UserClientProps userClientProps
    ) {
        return new UserClient(RestTemplateFactory.create(restTemplateBuilder, userClientProps))
    }
}
