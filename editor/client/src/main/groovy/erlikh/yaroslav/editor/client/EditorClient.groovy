package erlikh.yaroslav.editor.client

import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import org.springframework.web.client.RestTemplate

class EditorClient {

    private final RestTemplate restTemplate
    private final String baseUrl = "/api/v1/rules"

    EditorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    SmartLinkRuleDto[] getAll() {
        restTemplate.getForObject(baseUrl, SmartLinkRuleDto[].class)
    }
}
