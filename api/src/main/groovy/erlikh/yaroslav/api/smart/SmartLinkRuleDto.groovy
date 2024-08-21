package erlikh.yaroslav.api.smart

import com.fasterxml.jackson.annotation.JsonProperty

class SmartLinkRuleDto {

    @JsonProperty(value = "name", required = true)
    String name

    @JsonProperty(value = "type", required = true)
    String type

    @JsonProperty(value = "rule", required = true)
    String rule

    @JsonProperty(value = "link", required = true)
    String link

    SmartLinkRuleDto(String name, String type, String rule, String link) {
        this.name = name
        this.type = type
        this.rule = rule
        this.link = link
    }
}
