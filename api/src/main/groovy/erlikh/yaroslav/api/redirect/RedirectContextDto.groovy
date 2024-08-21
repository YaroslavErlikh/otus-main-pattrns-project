package erlikh.yaroslav.api.redirect

import com.fasterxml.jackson.annotation.JsonProperty

import java.time.LocalTime

class RedirectContextDto {

    @JsonProperty(value = "time", required = false)
    LocalTime time = null

    @JsonProperty(value = "device", required = false)
    String device = null
}