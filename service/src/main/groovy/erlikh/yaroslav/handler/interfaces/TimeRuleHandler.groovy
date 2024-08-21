package erlikh.yaroslav.handler.interfaces

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.api.rules.TimeRule
import erlikh.yaroslav.domain.Rule
import erlikh.yaroslav.domain.RuleContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TimeRuleHandler implements RuleHandler {

    @Autowired
    private ObjectMapper jsonObjectMapper

    @Override
    String handle(Rule rule, RuleContext context) {
        if (types().contains(rule.type())) {
            TimeRule timeRule = jsonObjectMapper.readValue(rule.rule(), TimeRule.class)
            def time = context.time()
            if (time != null && time.isAfter(timeRule.from) && time.isBefore(timeRule.to)) {
                return rule.link()
            }
        }
        return null
    }

    @Override
    List<String> types() {
        List.of("time")
    }
}
