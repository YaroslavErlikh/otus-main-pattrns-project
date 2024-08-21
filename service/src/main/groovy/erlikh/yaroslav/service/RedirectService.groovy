package erlikh.yaroslav.service

import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.domain.RuleContext
import erlikh.yaroslav.editor.client.EditorClient
import erlikh.yaroslav.handler.interfaces.RuleHandler
import erlikh.yaroslav.mapper.RuleConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RedirectService {

    private final String DEFAULT_URL = "default"

    @Autowired
    private List<RuleHandler> smartLinkRuleHandlers

    @Autowired
    private EditorClient editorClient

    String getLink(RuleContext context) {
        SmartLinkRuleDto[] linkRuleDtoArr = editorClient.getAll()
        Arrays.asList(linkRuleDtoArr).stream()
                .map(it -> RuleConverter.toDomain(it))
                .flatMap(rule -> smartLinkRuleHandlers.stream()
                        .map(handler -> handler.handle(rule, context))
                        .filter(result -> result != null))
                .findFirst()
                .orElse(DEFAULT_URL)
    }
}
