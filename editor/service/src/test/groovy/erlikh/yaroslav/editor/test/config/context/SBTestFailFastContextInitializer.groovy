package erlikh.yaroslav.editor.test.config.context

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

import java.util.concurrent.atomic.AtomicBoolean

import static ContextConfig.failFast

class SBTestFailFastContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private def flag = new AtomicBoolean()

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        Boolean error = flag.getAndSet(true)
        if (error) failFast()
    }
}
