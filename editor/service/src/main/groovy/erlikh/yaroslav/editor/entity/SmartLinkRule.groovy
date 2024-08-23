package erlikh.yaroslav.editor.entity


import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import static org.apache.commons.lang3.StringUtils.EMPTY

@Document("smart_link_rules")
class SmartLinkRule {

    @Id
    @NotNull
    String id = new ObjectId().toHexString()

    String name = EMPTY
    String type = EMPTY
    String rule = EMPTY
    String link = EMPTY

    SmartLinkRule() {
    }

    SmartLinkRule(String name, String type, String rule, String link) {
        this.name = name
        this.type = type
        this.rule = rule
        this.link = link
    }
}
