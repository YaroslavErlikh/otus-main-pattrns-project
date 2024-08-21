package erlikh.yaroslav.editor.repo

import erlikh.yaroslav.editor.entity.SmartLinkRule
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SmartLinkRuleRepository extends MongoRepository<SmartLinkRule, String> {

    List<SmartLinkRule> findByName(String name)
}