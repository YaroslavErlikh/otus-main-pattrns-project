package erlikh.yaroslav.editor.security

import com.auth0.jwt.JWT
import erlikh.yaroslav.api.jwt.JwtValidationRequestDto
import erlikh.yaroslav.jwt.client.JwtClient
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component

@Component
class EditorAuthenticationManager implements AuthenticationManager {

    private JwtClient jwtClient

    EditorAuthenticationManager(JwtClient jwtClient) {
        this.jwtClient = jwtClient
    }

    @Override
    Authentication authenticate(Authentication authentication) {
        def token = (authentication as BearerTokenAuthenticationToken).token

        def authority = JWT.decode(token).claims["roles"]?.asString()
        def authorities = List.of(new SimpleGrantedAuthority(authority))

        def authenticationToken = new EditorAuthenticationToken(token, authorities)
        authenticationToken.setAuthenticated(jwtClient.isTokenValid(new JwtValidationRequestDto(token)).isValid())

        return authenticationToken
    }
}
