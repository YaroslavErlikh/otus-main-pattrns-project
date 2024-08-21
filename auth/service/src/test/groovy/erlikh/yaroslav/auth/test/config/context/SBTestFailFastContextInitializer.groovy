package erlikh.yaroslav.auth.test.config.context

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

import java.util.concurrent.atomic.AtomicBoolean

class SBTestFailFastContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final AtomicBoolean flag = new AtomicBoolean()

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        boolean error = flag.getAndSet(true)
        if (error) {
            failFast()
        }
    }


    static void failFast() {
        println("""
    ####################################################
    #     SECOND APPLICATION CONTEXT START ATTEMPT.    #
    #     -----------------------------------------    #
    #  Please look upper for the reason why Spring is  #
    #  trying to recreate test context.  This can be   #
    #  caused by adding an @Import/@MockBean and some  #
    #  other annotations to the test class.            #
    #                                                  #
    #  This process will now exit immediately.         #
    ####################################################
        """)
        System.exit(-1)
    }
}
