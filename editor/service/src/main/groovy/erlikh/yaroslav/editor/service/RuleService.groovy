package erlikh.yaroslav.editor.service

import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.editor.dto.SmartLinkRuleUpdateDto
import erlikh.yaroslav.editor.entity.SmartLinkRule
import erlikh.yaroslav.editor.mapper.SmartLinkRuleConverter
import erlikh.yaroslav.editor.repo.SmartLinkRuleRepository
import org.springframework.stereotype.Service

@Service
class RuleService {

    private SmartLinkRuleRepository smartLinkRuleRepository

    RuleService(SmartLinkRuleRepository smartLinkRuleRepository) {
        this.smartLinkRuleRepository = smartLinkRuleRepository
    }

    SmartLinkRuleDto findById(String id) {
        def smartLinkRuleOpt = smartLinkRuleRepository.findById(id)
        if (smartLinkRuleOpt.isPresent()) {
            SmartLinkRuleConverter.toDto(smartLinkRuleOpt.get())
        }
    }

    List<SmartLinkRuleDto> findAll() {
        smartLinkRuleRepository.findAll().stream()
                .map { it -> SmartLinkRuleConverter.toDto(it) }
                .toList()
    }

    SmartLinkRuleDto add(SmartLinkRuleUpdateDto dto) {
        def entity = SmartLinkRuleConverter.toEntity(dto)
        def smartLinkRule = smartLinkRuleRepository.insert(entity)
        SmartLinkRuleConverter.toDto(smartLinkRule)
    }

    SmartLinkRuleDto update(String id, SmartLinkRuleUpdateDto dto) {
        def smartLinkRuleOpt = smartLinkRuleRepository.findById(id)
        def result = null
        if (smartLinkRuleOpt.isPresent()) {
            def rule = smartLinkRuleOpt.get()
            smartLinkRuleRepository.delete(rule)
            def linkRule = smartLinkRuleRepository.save(new SmartLinkRule(dto.name, dto.type, dto.rule, dto.link))
            result = SmartLinkRuleConverter.toDto(linkRule)
        }
        return result
    }

    void delete(String id) {
        def liknRuleOpt = smartLinkRuleRepository.findById(id)
        if (liknRuleOpt.isPresent()) {
            smartLinkRuleRepository.delete(liknRuleOpt.get())
        }
    }
}
