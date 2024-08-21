package erlikh.yaroslav.user.client

import erlikh.yaroslav.api.user.UserDto
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class UserClient {

    private String baseUrl = "/api/v1/user"

    private RestTemplate restTemplate

    UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    UserDto findByLogin(String login) {
        restTemplate.getForObject(UriComponentsBuilder.fromPath(baseUrl).queryParam("login", login).encode().toUriString(), UserDto.class)
    }
}
