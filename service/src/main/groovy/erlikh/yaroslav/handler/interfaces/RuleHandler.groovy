package erlikh.yaroslav.handler.interfaces

import erlikh.yaroslav.domain.Rule
import erlikh.yaroslav.domain.RuleContext

interface RuleHandler {

    String handle(Rule rule, RuleContext context)
    List<String> types()
}