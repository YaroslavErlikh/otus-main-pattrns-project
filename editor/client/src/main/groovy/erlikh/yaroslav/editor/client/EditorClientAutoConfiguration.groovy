package erlikh.yaroslav.editor.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import erlikh.yaroslav.common.rest.RestTemplateFactory
import erlikh.yaroslav.jwt.client.JwtClient
import erlikh.yaroslav.jwt.client.TechTokenHttpRequestInterceptor
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean

@AutoConfiguration
@EnableConfigurationProperties(EditorClientProps.class)
class EditorClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
    }

    @Bean
    EditorClient editorClient(RestTemplateBuilder restTemplateBuilder, EditorClientProps editorClientProps, JwtClient jwtClient) {
        new EditorClient(RestTemplateFactory.create(restTemplateBuilder, editorClientProps, new TechTokenHttpRequestInterceptor(jwtClient)))
    }
}