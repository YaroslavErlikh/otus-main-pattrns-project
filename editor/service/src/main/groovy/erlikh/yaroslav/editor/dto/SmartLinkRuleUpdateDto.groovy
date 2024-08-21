package erlikh.yaroslav.editor.dto

import com.fasterxml.jackson.annotation.JsonProperty

class SmartLinkRuleUpdateDto {

    @JsonProperty(value = "name", required = true)
    String name

    @JsonProperty(value = "type", required = true)
    String type

    @JsonProperty(value = "rule", required = true)
    String rule

    @JsonProperty(value = "link", required = true)
    String link

    SmartLinkRuleUpdateDto() {
    }

    SmartLinkRuleUpdateDto(String name, String type, String rule, String link) {
        this.name = name
        this.type = type
        this.rule = rule
        this.link = link
    }
}
