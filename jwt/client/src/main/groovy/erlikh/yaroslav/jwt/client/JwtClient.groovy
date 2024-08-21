package erlikh.yaroslav.jwt.client

import erlikh.yaroslav.api.jwt.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class JwtClient {

    private String baseUrl = "/api/v1/jwt"

    private RestTemplate restTemplate

    JwtClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    JwtGenerationResponseDto generate(JwtGenerationRequestDto jwtGenerationReqDto) {
        restTemplate.postForObject("$baseUrl/generate", jwtGenerationReqDto, JwtGenerationResponseDto.class)
    }

    JwtGenerationResponseDto techToken() {
        restTemplate.postForObject("$baseUrl/tech-token", null, JwtGenerationResponseDto.class)
    }

    JwtUsernameResponseDto getUsername(String jwtToken) {
        restTemplate.getForObject(
                UriComponentsBuilder.fromPath("$baseUrl/username").queryParam("token", jwtToken).encode().toUriString(),
                JwtUsernameResponseDto.class
        )
    }

    JwtValidationResponseDto isTokenValid(JwtValidationRequestDto jwtValidationReqDto) {
        restTemplate.postForObject("$baseUrl/validate", jwtValidationReqDto, JwtValidationResponseDto.class)
    }
}
