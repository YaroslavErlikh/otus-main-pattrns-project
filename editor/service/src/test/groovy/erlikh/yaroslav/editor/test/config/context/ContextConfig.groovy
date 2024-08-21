package erlikh.yaroslav.editor.test.config.context

class ContextConfig {

    static void failFast() {
        System.out.println("""
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
