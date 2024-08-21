package erlikh.yaroslav.user.controller

import erlikh.yaroslav.api.user.UserDto
import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.user.entity.UserEntity
import erlikh.yaroslav.user.test.AbstractSBTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@DisplayName("Контроллер управления пользователями")
class UserControllerSBTest extends AbstractSBTest {

    private String basePath = "/api/v1/user"

    private static final String login = "login"
    def userEntity = new UserEntity(
            name: "name",
            login: login,
            password: "pass",
            role: UserRole.USER
    )

    @DisplayName("должен возвращать пользователя по логину")
    @Test
    void 'should return expected rule by id'() {
        // given
        userRepository.save(userEntity)

        // when
        String response = mockMvc.perform(get(basePath)
                .param("login", login)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        UserDto actual = jsonObjectMapper.readValue(response, UserDto.class)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(userEntity)
    }
}
