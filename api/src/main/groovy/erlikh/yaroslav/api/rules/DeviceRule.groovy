package erlikh.yaroslav.api.rules

import com.fasterxml.jackson.annotation.JsonProperty

class DeviceRule {

    @JsonProperty(value = "device", required = true)
    final String device

    DeviceRule() {
    }

    DeviceRule(String device) {
        this.device = device
    }
}