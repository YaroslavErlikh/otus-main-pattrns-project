package erlikh.yaroslav.auth.web

import com.github.tomakehurst.wiremock.client.WireMock
import erlikh.yaroslav.api.auth.AuthRequestDto
import erlikh.yaroslav.api.user.UserDto
import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.auth.test.AbstractSBTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import static com.github.tomakehurst.wiremock.client.WireMock.okJson
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import static erlikh.yaroslav.common.test.TestHelper.*

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.security.crypto.password.PasswordEncoder

@DisplayName("Контроллер аутентификации")
class AuthControllerSBTest extends AbstractSBTest {

    @Autowired
    public PasswordEncoder passwordEncoder

    private final String basePath = "/api/v1/auth"

    @DisplayName("должен ожидаемо аутентифицировать пользователя")
    @Test
    void 'success_user_auth'() {
        // given
        String login = "login"
        String password = "pass"

        UserDto user = new UserDto("username", login, passwordEncoder.encode(password), UserRole.USER)
        String userResponse = new ObjectMapper().writeValueAsString(user)
        wm.stubFor(WireMock.get(urlPathEqualTo("/user/api/v1/user")).willReturn(okJson(userResponse)))

        wm.stubFor(WireMock.post(urlEqualTo("/jwt/api/v1/jwt/generate")).willReturn(okJson("{\"token\": \"" + token + "\"}")))

        AuthRequestDto authReqDto = new AuthRequestDto(login, password)

        // when + then
        mockMvc.perform(post(basePath + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authReqDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.token').value(token))
    }
}