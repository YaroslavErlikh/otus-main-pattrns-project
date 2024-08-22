package erlikh.yaroslav.auth.test

import com.github.tomakehurst.wiremock.WireMockServer
import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.auth.test.config.WireMockInitializer
import erlikh.yaroslav.auth.test.config.context.SBTestFailFastContextInitializer
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
@ContextConfiguration(initializers = [ WireMockInitializer.class, SBTestFailFastContextInitializer.class])
class AbstractSBTest {

    @Autowired
    public MockMvc mockMvc

    @Autowired
    public ObjectMapper jsonObjectMapper

    @Autowired
    public WireMockServer wm

    @AfterEach
    void tearDownSbTest() {
        wm.resetAll()
        Mockito.clearInvocations()
    }
}
