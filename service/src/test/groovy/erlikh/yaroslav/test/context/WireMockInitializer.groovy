package erlikh.yaroslav.test.context

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        def wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort())
        wireMockServer.start()
        applicationContext.beanFactory.registerSingleton("wm", wireMockServer)
        applicationContext.addApplicationListener(event -> {
            if (event instanceof ContextClosedEvent) {
                wireMockServer.stop()
            }
        })
        TestPropertyValues
                .of(Map.of("wiremock.server.base-url", wireMockServer.baseUrl()))
                .applyTo(applicationContext)
    }
}
