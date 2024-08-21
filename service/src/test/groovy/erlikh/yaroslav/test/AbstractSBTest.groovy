package erlikh.yaroslav.test

import com.fasterxml.jackson.databind.ObjectMapper
import erlikh.yaroslav.test.context.SBTestFailFastContextInitializer
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
@ContextConfiguration(initializers = SBTestFailFastContextInitializer.class)
class AbstractSBTest {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper jsonObjectMapper

    @AfterEach
    void tearDownSbTest() {
        Mockito.clearInvocations()
    }
}
