package erlikh.yaroslav.common.rest


import java.time.Duration

class RestProps {

    private String baseUrl
    private Duration connectTimeout
    private Duration readTimeout

    RestProps() {
        this.baseUrl = "http://localhost:8080"
        this.connectTimeout = Duration.ofSeconds(15)
        this.readTimeout = Duration.ofSeconds(40)
    }

    RestProps(String baseUrl, Duration connectTimeout, Duration readTimeout) {
        this.baseUrl = baseUrl
        this.connectTimeout = connectTimeout
        this.readTimeout = readTimeout
    }

    public static final RestProps DEFAULT = new RestProps()

    String getBaseUrl() {
        return baseUrl
    }

    Duration getConnectTimeout() {
        return connectTimeout
    }

    Duration getReadTimeout() {
        return readTimeout
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl
    }

    void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout
    }

    void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout
    }
}
