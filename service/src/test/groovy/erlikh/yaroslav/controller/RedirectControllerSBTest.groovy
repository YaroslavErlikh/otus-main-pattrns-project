package erlikh.yaroslav.controller

import com.github.tomakehurst.wiremock.client.WireMock
import erlikh.yaroslav.api.redirect.RedirectContextDto
import erlikh.yaroslav.api.rules.DeviceRule
import erlikh.yaroslav.api.rules.TimeRule
import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.test.AbstractSBTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import java.time.LocalTime

import static com.github.tomakehurst.wiremock.client.WireMock.okJson
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static erlikh.yaroslav.common.test.TestHelper.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("Контроллер редиректа")
class RedirectControllerSBTest extends AbstractSBTest {
    private final String basePath = "/api/v1"

    @BeforeEach
    void setUp() {
        wm.stubFor(WireMock.post(urlEqualTo("/jwt/api/v1/jwt/validate")).willReturn(okJson("{\"isValid\": true}")))
        wm.stubFor(WireMock.post(urlEqualTo("/jwt/api/v1/jwt/tech-token")).willReturn(okJson("{\"token\": \"" + techToken + "\"}")))
    }

    @DisplayName("должен ожидаемо перенаправлять запрос по времени")
    @Test
    void shouldExpectedlyRedirectByTimeRule() {
        // given
        String timeRule = jsonObjectMapper.writeValueAsString(new TimeRule(LocalTime.of(9, 0), LocalTime.of(12, 0)))
        List<SmartLinkRuleDto> smartLinkRules = new ArrayList<>()
        smartLinkRules.add(new SmartLinkRuleDto("time-rule-1", "time", timeRule, "time-rule-1-link"))
        String getSmartLinkRulesResponse = jsonObjectMapper.writeValueAsString(smartLinkRules)
        wm.stubFor(WireMock.get(urlEqualTo("/editor/api/v1/rules")).willReturn(okJson(getSmartLinkRulesResponse)))

        RedirectContextDto contextDto = new RedirectContextDto(LocalTime.of(11, 0), "PC")

        // when + then
        mockMvc.perform(post(basePath + "/redirect")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(contextDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.link').value("time-rule-1-link"))
    }

    @DisplayName("должен ожидаемо перенаправлять запрос по устройству пользователя")
    @Test
    void shouldExpectedlyRedirectByDeviceRule() {
        // given
        String deviceRule = jsonObjectMapper.writeValueAsString(new DeviceRule("PC"))
        List<SmartLinkRuleDto> smartLinkRules = new ArrayList<>()
        smartLinkRules.add(new SmartLinkRuleDto("device-rule-1", "device", deviceRule, "device-rule-1-link"))
        String getSmartLinkRulesResponse = jsonObjectMapper.writeValueAsString(smartLinkRules)
        wm.stubFor(WireMock.get(urlEqualTo("/editor/api/v1/rules")).willReturn(okJson(getSmartLinkRulesResponse)))

        RedirectContextDto contextDto = new RedirectContextDto(LocalTime.of(11, 0), "PC")

        // when + then
        mockMvc.perform(post(basePath + "/redirect")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(contextDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.link').value("device-rule-1-link"))
    }
}