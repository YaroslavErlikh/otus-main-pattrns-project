package erlikh.yaroslav.security

import erlikh.yaroslav.api.jwt.JwtValidationRequestDto
import erlikh.yaroslav.jwt.client.JwtClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.security.core.Authentication

@Component
class ServiceAuthenticationManager implements AuthenticationManager {

    @Autowired
    private JwtClient jwtClient

    @Override
    Authentication authenticate(Authentication authentication) {
        def token = (authentication as BearerTokenAuthenticationToken).token
        authentication.setAuthenticated(jwtClient.isTokenValid(new JwtValidationRequestDto(token)).isValid() == true)
        return authentication
    }
}
