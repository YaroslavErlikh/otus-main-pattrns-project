package erlikh.yaroslav.auth.controller

import erlikh.yaroslav.api.auth.AuthRequestDto
import erlikh.yaroslav.api.auth.AuthResponseDto
import erlikh.yaroslav.api.jwt.JwtGenerationRequestDto
import erlikh.yaroslav.jwt.client.JwtClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    private static Logger log = LoggerFactory.getLogger(AuthController.class)

    @Autowired
    private final UserDetailsService userDetailsService

    @Autowired
    private final JwtClient jwtClient

    @Autowired
    private final AuthenticationManager authenticationManager

    @PostMapping("/login")
    ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Login request received for user: ${authRequestDto.login()}")
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.login(),
                        authRequestDto.password()
                )
        )
        final userDetails = userDetailsService.loadUserByUsername(authRequestDto.login())
        final token = jwtClient.generate(
                new JwtGenerationRequestDto(
                        userDetails.username,
                        userDetails.authorities.first().authority
                )
        ).token()

        return ResponseEntity.ok(new AuthResponseDto(token: token))
    }
}
