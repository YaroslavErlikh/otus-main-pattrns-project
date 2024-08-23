package erlikh.yaroslav.auth.client

import erlikh.yaroslav.common.rest.RestProps
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.time.Duration

@ConfigurationProperties("msa.clients.auth")
class AuthClientProps extends RestProps {

    AuthClientProps() {
        super("http://localhost:8080", Duration.ofSeconds(15), Duration.ofSeconds(40))
    }
}
