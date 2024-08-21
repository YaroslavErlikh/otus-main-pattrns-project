package erlikh.yaroslav.jwt.controller

import erlikh.yaroslav.api.jwt.JwtGenerationRequestDto
import erlikh.yaroslav.api.jwt.JwtValidationRequestDto
import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.jwt.test.AbstractSBTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

import static erlikh.yaroslav.common.test.TestHelper.token
import static org.hamcrest.Matchers.is
import static org.mockito.ArgumentMatchers.anyString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("Контроллер JWT")
class JwtControllerSBTest extends AbstractSBTest {

    private final String basePath = "/api/v1/jwt"

    @DisplayName("должен возвращать ожидаемое имя пользователя")
    @Test
    void 'should return expected username'() {
        mockMvc.perform(get("$basePath/username")
                .param("token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.username', is("user")))
        .andReturn()
    }

    @DisplayName("должен генерировать токен")
    @Test
    void 'should generate token'() {
        def requestDto = new JwtGenerationRequestDto("username", UserRole.USER.name())

        mockMvc.perform(post("$basePath/generate")
                .header(MediaType.APPLICATION_JSON_VALUE, jsonObjectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.token', anyString()))
        .andReturn()
    }

    @DisplayName("должен возвращать технический токен")
    @Test
    void 'should generate technical token'() {
        mockMvc.perform(post("$basePath/tech-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.token', anyString()))
        .andReturn()
    }

    @DisplayName("должен проверять токен")
    @Test
    void 'should validate token'() {
        def requestDto = new JwtValidationRequestDto(token)

        mockMvc.perform(post("$basePath/validate")
                .header(MediaType.APPLICATION_JSON_VALUE, jsonObjectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.isValid', is(true)))
        .andReturn()
    }
}
