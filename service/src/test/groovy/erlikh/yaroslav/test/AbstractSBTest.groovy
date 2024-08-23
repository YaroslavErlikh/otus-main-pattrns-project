package erlikh.yaroslav.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import erlikh.yaroslav.test.context.SBTestFailFastContextInitializer
import erlikh.yaroslav.test.context.WireMockInitializer
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(
        initializers = [ WireMockInitializer.class,  SBTestFailFastContextInitializer.class ]
)
class AbstractSBTest {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper jsonObjectMapper

    @Autowired
    WireMockServer wm

    @AfterEach
    void tearDownSbTest() {
        wm.resetAll()
        Mockito.clearInvocations()
    }
}
