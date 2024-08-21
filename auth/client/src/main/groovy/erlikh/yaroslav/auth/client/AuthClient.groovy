package erlikh.yaroslav.auth.client

import erlikh.yaroslav.api.auth.AuthRequestDto
import erlikh.yaroslav.api.auth.AuthResponseDto
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate

class AuthClient {

    private RestTemplate restTemplate

    private final String BASE_URL = "/api/v1/auth"

    AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    AuthResponseDto login(AuthRequestDto authRequestDto) {
        restTemplate.postForObject("${BASE_URL}/login", new HttpEntity(authRequestDto), AuthResponseDto.class)
    }
}