package erlikh.yaroslav.api.rules

import com.fasterxml.jackson.annotation.JsonProperty

import java.time.LocalTime

class TimeRule {

    @JsonProperty(value = "from", required = true)
    final LocalTime from

    @JsonProperty(value = "to", required = true)
    final LocalTime to

    TimeRule() {
    }

    TimeRule(LocalTime from, LocalTime to) {
        this.from = from
        this.to = to
    }
}