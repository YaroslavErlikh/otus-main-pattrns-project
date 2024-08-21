package erlikh.yaroslav.editor.config

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.api.rules.DeviceRule
import erlikh.yaroslav.api.rules.TimeRule
import erlikh.yaroslav.editor.entity.SmartLinkRule
import erlikh.yaroslav.editor.repo.SmartLinkRuleRepository
import org.springframework.stereotype.Component
import org.springframework.context.event.EventListener
import org.springframework.boot.context.event.ApplicationReadyEvent

import java.time.LocalTime

@Component
class DbPopulation {

    private final SmartLinkRuleRepository repository
    private final ObjectMapper objectMapper

    DbPopulation(SmartLinkRuleRepository repository, ObjectMapper objectMapper) {
        this.repository = repository
        this.objectMapper = objectMapper
    }

    @EventListener(ApplicationReadyEvent.class)
    void populate() {
        def timeRule = objectMapper.writeValueAsString(new TimeRule(LocalTime.of(9, 0), LocalTime.of(12, 0)))
        if (repository.findByName("time-rule").isEmpty())
            repository.save(new SmartLinkRule("time-rule", "time", timeRule, "time-rule-link"))

        def deviceRule = objectMapper.writeValueAsString(new DeviceRule("PC"))
        if (repository.findByName("device-rule").isEmpty())
            repository.save(new SmartLinkRule("device-rule", "device", deviceRule, "device-rule-link"))
    }
}
