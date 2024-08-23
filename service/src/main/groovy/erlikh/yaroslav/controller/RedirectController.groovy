package erlikh.yaroslav.controller

import erlikh.yaroslav.api.redirect.RedirectContextDto
import erlikh.yaroslav.api.redirect.RedirectResponseDto
import erlikh.yaroslav.mapper.RuleConverter
import erlikh.yaroslav.service.RedirectService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
class RedirectController {

    @Autowired
    private RedirectService redirectService

    @PostMapping("/redirect")
    ResponseEntity<RedirectResponseDto> redirect(@RequestBody RedirectContextDto contextDto) {
        def dto = new RedirectResponseDto(redirectService.getLink(RuleConverter.toDomain(contextDto)))
        return ResponseEntity.ok(dto)
    }
}
