package erlikh.yaroslav.mapper

import erlikh.yaroslav.api.redirect.RedirectContextDto
import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.domain.Rule
import erlikh.yaroslav.domain.RuleContext

class RuleConverter {

    static Rule toDomain(SmartLinkRuleDto dto) {
        return new Rule(dto.name, dto.type, dto.rule, dto.link)
    }

    static RuleContext toDomain(RedirectContextDto dto) {
        return new RuleContext(dto.time, dto.device)
    }
}
