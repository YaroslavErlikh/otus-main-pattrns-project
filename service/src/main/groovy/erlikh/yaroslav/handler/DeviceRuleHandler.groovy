package erlikh.yaroslav.handler

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.api.rules.DeviceRule
import erlikh.yaroslav.domain.Rule
import erlikh.yaroslav.domain.RuleContext
import erlikh.yaroslav.handler.interfaces.RuleHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DeviceRuleHandler implements RuleHandler {

    @Autowired
    private ObjectMapper jsonObjectMapper

    @Override
    String handle(Rule rule, RuleContext context) {
        if (types().contains(rule.type())) {
            DeviceRule deviceRule = jsonObjectMapper.readValue(rule.rule(), DeviceRule.class)
            if (deviceRule.getDevice().equals(context.device())) {
                return rule.link()
            }
        }
        return null
    }

    @Override
    List types() {
        List.of("device")
    }
}
