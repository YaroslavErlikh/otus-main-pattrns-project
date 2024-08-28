package erlikh.yaroslav.editor.mapper

import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.editor.dto.SmartLinkRuleUpdateDto
import erlikh.yaroslav.editor.entity.SmartLinkRule

class SmartLinkRuleConverter {
//
//    static SmartLinkRuleDto toDto(String name, String type, String rule, String link) {
//        new SmartLinkRuleDto(name, type, rule, link)
//    }

    static SmartLinkRuleDto toDto(SmartLinkRule smartLinkRule) {
        new SmartLinkRuleDto(smartLinkRule.name, smartLinkRule.type, smartLinkRule.rule, smartLinkRule.link)
    }

//    static SmartLinkRule toEntity(String name, String type, String rule, String link) {
//        new SmartLinkRule(name, type, rule, link)
//    }

    static SmartLinkRule toEntity(SmartLinkRuleUpdateDto dto) {
        new SmartLinkRule(dto.name, dto.type, dto.rule, dto.link)
    }
}
