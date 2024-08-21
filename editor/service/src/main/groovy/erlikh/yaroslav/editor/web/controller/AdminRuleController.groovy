package erlikh.yaroslav.editor.web.controller

import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.editor.dto.SmartLinkRuleUpdateDto
import erlikh.yaroslav.editor.mapper.ResponseEntityConverter
import erlikh.yaroslav.editor.service.RuleService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin/rules")
@SecurityRequirement(name = "bearerAuth")
class AdminRuleController {

    @Autowired
    private RuleService ruleService

    @GetMapping("/{id}")
    ResponseEntity<SmartLinkRuleDto> findById(@PathVariable("id") String id) {
        return ResponseEntityConverter.toResponseEntity(ruleService.findById(id))
    }

    @PostMapping
    ResponseEntity<SmartLinkRuleDto> add(@RequestBody SmartLinkRuleUpdateDto dto) {
        return ResponseEntityConverter.toResponseEntity(ruleService.add(dto))
    }

    @PutMapping("/{id}")
    ResponseEntity<SmartLinkRuleDto> updateById(
            @PathVariable(value = "id") String id,
            @RequestBody SmartLinkRuleUpdateDto dto
    ) {
        return ResponseEntityConverter.toResponseEntity(ruleService.update(id, dto))
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteEmployeeById(@PathVariable(value = "id") String id) {
        return ResponseEntityConverter.toResponseEntity(ruleService.delete(id))
    }
}
