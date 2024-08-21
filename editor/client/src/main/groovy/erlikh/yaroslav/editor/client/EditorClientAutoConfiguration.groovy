package erlikh.yaroslav.editor.client

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.common.rest.RestTemplateFactory
import erlikh.yaroslav.jwt.client.JwtClient
import erlikh.yaroslav.jwt.client.TechTokenHttpRequestInterceptor
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import ru.sokomishalov.commons.core.serialization.ObjectMapperHelperKt

@AutoConfiguration
@EnableConfigurationProperties(EditorClientProps.class)
class EditorClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }

    @Bean
    EditorClient editorClient(RestTemplateBuilder restTemplateBuilder, EditorClientProps authClientProps, JwtClient jwtClient) {
        new EditorClient(
                new RestTemplateFactory().create(
                        restTemplateBuilder,
                        authClientProps,
                        new TechTokenHttpRequestInterceptor(jwtClient)
                )
        )
    }
}
