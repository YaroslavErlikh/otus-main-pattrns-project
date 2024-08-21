package erlikh.yaroslav.editor.web.controller

import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.editor.mapper.ResponseEntityConverter
import erlikh.yaroslav.editor.service.RuleService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rules")
@SecurityRequirement(name = "bearerAuth")
class RuleController {

    @Autowired
    private RuleService ruleService

    @GetMapping
    ResponseEntity<List<SmartLinkRuleDto>> getAll() {
        return ResponseEntityConverter.toResponseEntity(ruleService.findAll())
    }
}
