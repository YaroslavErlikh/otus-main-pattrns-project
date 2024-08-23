package erlikh.yaroslav.editor.web.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.github.tomakehurst.wiremock.client.WireMock
import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.common.test.TestHelper
import erlikh.yaroslav.editor.entity.SmartLinkRule
import erlikh.yaroslav.editor.test.AbstractSBTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static erlikh.yaroslav.common.test.TestHelper.token
import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("Контролер работы с правилами")
class RuleControllerSBTest extends AbstractSBTest {

    private final String basePath = "/api/v1/rules"

    def ruleEntity = new SmartLinkRule("name", "type", "rule", "link")
    def ruleEntity2 = new SmartLinkRule("name2", "type2", "rule2", "link2")

    def rule = new SmartLinkRuleDto("name", "type", "rule", "link")
    def rule2 = new SmartLinkRuleDto("name2", "type2", "rule2", "link2")

    @BeforeEach
    void setUp() {
        wm.stubFor(WireMock.post(urlEqualTo("/jwt/api/v1/jwt/validate"))
                .willReturn(aResponse().withBody("{\"isValid\": true}").withHeader("Content-Type", "application/json")))
    }

    @DisplayName("должен возвращать ожидаемые правила")
    @Test
    void shouldReturnExpectedRules() {
        // given
        ruleRepository.saveAll(Arrays.asList(ruleEntity, ruleEntity2))

        // when
        String response = mockMvc.perform(get(basePath)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        List<SmartLinkRuleDto> actual = jsonObjectMapper.readValue(response, new TypeReference<List<SmartLinkRuleDto>>() {
        })

        // then
        assertThat(actual.size()).isEqualTo(2)
        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(rule)
        assertThat(actual.get(1)).usingRecursiveComparison().isEqualTo(rule2)
    }
}