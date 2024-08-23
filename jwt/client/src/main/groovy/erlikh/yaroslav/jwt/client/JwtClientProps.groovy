package erlikh.yaroslav.jwt.client

import erlikh.yaroslav.common.rest.RestProps
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.time.Duration

@ConfigurationProperties("msa.clients.jwt")
class JwtClientProps extends RestProps {

    JwtClientProps() {
        super("http://localhost:8080", Duration.ofSeconds(15), Duration.ofSeconds(40))
    }

    JwtClientProps(String baseUrl, Duration connectTimeout, Duration readTimeout) {
        super(baseUrl, connectTimeout, readTimeout)
    }
}
