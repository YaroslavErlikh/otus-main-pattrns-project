package erlikh.yaroslav.common.rest

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

import java.nio.charset.StandardCharsets

class RestTemplateFactory {

    static RestTemplate create(RestTemplateBuilder restTemplateBuilder) {
        create(restTemplateBuilder, RestProps.DEFAULT)
    }

    static RestTemplate create(RestTemplateBuilder restTemplateBuilder, RestProps restProps, ClientHttpRequestInterceptor... interceptors) {
        RestTemplate restTemplate = restTemplateBuilder
                .rootUri(restProps.baseUrl)
                .setConnectTimeout(restProps.connectTimeout)
                .setReadTimeout(restProps.readTimeout)
                .additionalInterceptors(interceptors)
                .build()

        for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8)
            }
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8)
            }
        }

        return restTemplate
    }
}