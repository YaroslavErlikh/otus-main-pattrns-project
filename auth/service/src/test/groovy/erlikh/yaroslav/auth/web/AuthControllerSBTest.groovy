package erlikh.yaroslav.auth.web

import erlikh.yaroslav.api.user.UserDto
import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.auth.test.AbstractSBTest
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics
import org.jetbrains.annotations.NotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class AuthControllerSBTest extends AbstractSBTest {

    @Autowired
    public PasswordEncoder passwordEncoder

    private final String basePath = "/api/v1/auth"

    @Test
    void 'success_user_auth'() {
        String login = "login"
        String password = "pass"
        UserDto user = new UserDto("username", login, passwordEncoder.encode((CharSequence)password), UserRole.USER)
        final String userResponse = jsonObjectMapper.writeValueAsString(user)
        wm.get { urlPath equalTo "/user/api/v1/user" }.returnsJson { body = userResponse }

        wm.post { url equalTo "/jwt/api/v1/jwt/generate" }.returnsJson { body = """{"token": "$token"}""" }

        def authReqDto = AuthReqDto(login, password)

        // when + then
        mockMvc.post("${basePath}/login") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonObjectMapper.writeValueAsString(authReqDto)
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
            jsonPath('$.token') { value(token) }
        }
    }
}
