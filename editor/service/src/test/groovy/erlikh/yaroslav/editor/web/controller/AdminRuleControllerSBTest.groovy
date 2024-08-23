package erlikh.yaroslav.editor.web.controller

import com.github.tomakehurst.wiremock.client.WireMock
import erlikh.yaroslav.api.smart.SmartLinkRuleDto
import erlikh.yaroslav.editor.dto.SmartLinkRuleUpdateDto
import erlikh.yaroslav.editor.entity.SmartLinkRule
import erlikh.yaroslav.editor.test.AbstractSBTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import static com.github.tomakehurst.wiremock.client.WireMock.okJson
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static erlikh.yaroslav.common.test.TestHelper.adminToken
import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("Контроллер управления правилами")
class AdminRuleControllerSBTest extends AbstractSBTest {

    private String basePath = "/api/v1/admin/rules"

    private SmartLinkRule ruleEntity = new SmartLinkRule("name", "type", "rule", "link")
    private SmartLinkRuleDto rule = new SmartLinkRuleDto("name", "type", "rule", "link")

    @BeforeEach
    void setUp() {
        wm.stubFor(WireMock.post(urlEqualTo("/jwt/api/v1/jwt/validate"))
                .willReturn(okJson("{\"isValid\": true}")))
    }

    @DisplayName("должен возвращать ожидаемое правило по id")
    @Test
    void shouldReturnExpectedRuleById() throws Exception {
        // given
        def ruleId = ruleRepository.save(ruleEntity).getId()

        // when
        String response = mockMvc.perform(get(basePath + "/" + ruleId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        SmartLinkRuleDto actual = jsonObjectMapper.readValue(response, SmartLinkRuleDto.class)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(rule)
    }

    @DisplayName("должен добавлять ожидаемое правило")
    @Test
    void shouldAddExpectedRule() throws Exception {
        // given
        SmartLinkRuleUpdateDto newRule = new SmartLinkRuleUpdateDto("newRule", "newType", "{}", "newLink")

        // when
        String response = mockMvc.perform(post(basePath)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(newRule)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        SmartLinkRuleDto actual = jsonObjectMapper.readValue(response, SmartLinkRuleDto.class)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(newRule)
    }

    @DisplayName("должен обновлять ожидаемое правило")
    @Test
    void shouldUpdateExpectedRule() throws Exception {
        // given
        String ruleId = ruleRepository.save(ruleEntity).getId()
        SmartLinkRuleUpdateDto updatedRule = new SmartLinkRuleUpdateDto("updatedRule", "updatedType", "{}", "updatedLink")

        // when
        String response = mockMvc.perform(put(basePath + "/" + ruleId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObjectMapper.writeValueAsString(updatedRule)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
        SmartLinkRuleDto actual = jsonObjectMapper.readValue(response, SmartLinkRuleDto.class)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(updatedRule)
    }

    @DisplayName("должен удалять ожидаемое правило")
    @Test
    void shouldDeleteExpectedRule() throws Exception {
        // given
        def ruleId = ruleRepository.save(ruleEntity).getId()

        // when
        mockMvc.perform(delete(basePath + "/" + ruleId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ${adminToken}"))
                .andExpect(status().isOk())

        // then
        assertThat(ruleRepository.findById(ruleId)).isEmpty()
    }
}