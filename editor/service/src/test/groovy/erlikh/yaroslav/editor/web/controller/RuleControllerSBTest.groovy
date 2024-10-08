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

import static com.github.tomakehurst.wiremock.client.WireMock.okJson
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static erlikh.yaroslav.common.test.TestHelper.token
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("Контролер работы с правилами")
class RuleControllerSBTest extends AbstractSBTest {

    private final String basePath = "/api/v1/rules"

    def ruleEntity = new SmartLinkRule("name", "type", "rule", "link")
    def ruleEntity2 = new SmartLinkRule("name2", "type2", "rule2", "link2")

    @BeforeEach
    void setUp() {
        wm.stubFor(WireMock.post(urlEqualTo("/jwt/api/v1/jwt/validate")).willReturn(okJson("{\"isValid\": true}")))
    }

    @DisplayName("должен возвращать ожидаемые правила")
    @Test
    void shouldReturnExpectedRules() {
        // given
        ruleRepository.saveAll(List.of(ruleEntity, ruleEntity2))

        // when
        String response = mockMvc.perform(get(basePath)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        List<SmartLinkRuleDto> actual = jsonObjectMapper.readValue(response, new TypeReference<List<SmartLinkRuleDto>>() {
        })

        // then
        assertTrue(actual.stream()
                .anyMatch {rule -> ruleEntity.name == rule.name
                        || ruleEntity2.name == rule.name
                })
    }
}