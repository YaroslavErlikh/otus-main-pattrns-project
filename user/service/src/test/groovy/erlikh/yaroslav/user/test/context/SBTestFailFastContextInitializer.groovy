package erlikh.yaroslav.user.test.context


import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

import java.util.concurrent.atomic.AtomicBoolean

class SBTestFailFastContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private def flag = new AtomicBoolean()

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        Boolean error = flag.getAndSet(true)
        if (error) ContextConfig.failFast()
    }
}
