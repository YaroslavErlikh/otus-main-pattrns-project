package erlikh.yaroslav.editor.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import erlikh.yaroslav.editor.repo.SmartLinkRuleRepository
import erlikh.yaroslav.editor.test.config.WireMockInitializer
import erlikh.yaroslav.editor.test.config.context.SBTestFailFastContextInitializer
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ActiveProfiles("test")
@ContextConfiguration(
        initializers = { WireMockInitializer.class; SBTestFailFastContextInitializer.class }
)
class AbstractSBTest {

    @Autowired
    SmartLinkRuleRepository ruleRepository

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper jsonObjectMapper

    @Autowired
    WireMockServer wm

    @AfterEach
    void tearDownSbTest() {
        ruleRepository.deleteAll()
        wm.resetAll()
        Mockito.clearInvocations()
    }
}
