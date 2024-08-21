package erlikh.yaroslav.api.redirect

import com.fasterxml.jackson.annotation.JsonProperty

class RedirectResponseDto {

    @JsonProperty(value = "link", required = true)
    String link
}
