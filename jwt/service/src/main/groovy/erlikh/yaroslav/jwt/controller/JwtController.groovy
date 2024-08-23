package erlikh.yaroslav.jwt.controller

import erlikh.yaroslav.api.jwt.JwtGenerationRequestDto
import erlikh.yaroslav.api.jwt.JwtGenerationResponseDto
import erlikh.yaroslav.api.jwt.JwtUsernameResponseDto
import erlikh.yaroslav.api.jwt.JwtValidationRequestDto
import erlikh.yaroslav.api.jwt.JwtValidationResponseDto
import erlikh.yaroslav.jwt.mapper.JwtGenerationConverter
import erlikh.yaroslav.jwt.service.JwtTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/jwt")
class JwtController {

    @Autowired
    private JwtTokenService jwtTokenService

    @GetMapping("/username")
    ResponseEntity<JwtUsernameResponseDto> getUsername(@RequestParam(value = "token", required = true) String token) {
        return ResponseEntity.ok(new JwtUsernameResponseDto(jwtTokenService.getUsername(token)))
    }

    @PostMapping("/generate")
    ResponseEntity<JwtGenerationResponseDto> generate(@RequestBody JwtGenerationRequestDto jwtGenerationReqDto) {
        return ResponseEntity.ok(
                new JwtGenerationResponseDto(jwtTokenService.generateToken(JwtGenerationConverter.toUser(jwtGenerationReqDto)))
        )
    }

    @PostMapping("/tech-token")
    ResponseEntity<JwtGenerationResponseDto> techToken() {
        return ResponseEntity.ok(new JwtGenerationResponseDto(jwtTokenService.techToken()))
    }

    @PostMapping("/validate")
    ResponseEntity<JwtValidationResponseDto> isTokenValid(@RequestBody JwtValidationRequestDto jwtValidationReqDto) {
        def dto = new JwtValidationResponseDto(jwtTokenService.isTokenValid(jwtValidationReqDto.token()))
        return ResponseEntity.ok(dto)
    }
}
