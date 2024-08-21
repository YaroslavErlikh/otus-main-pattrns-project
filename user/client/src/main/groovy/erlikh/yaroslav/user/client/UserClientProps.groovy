package erlikh.yaroslav.user.client

import erlikh.yaroslav.common.rest.RestProps
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.time.Duration

@Component
@ConfigurationProperties("msa.clients.user")
class UserClientProps extends RestProps {

    UserClientProps() {
        super("http://localhost:8080", Duration.ofSeconds(15), Duration.ofSeconds(40))
    }

    UserClientProps(String baseUrl, Duration connectTimeout, Duration readTimeout) {
        super(baseUrl, connectTimeout, readTimeout)
    }
}
