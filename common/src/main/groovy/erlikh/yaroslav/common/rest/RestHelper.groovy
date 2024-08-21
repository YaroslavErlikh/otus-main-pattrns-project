package erlikh.yaroslav.common.rest

class RestHelper {

    private static final String[] AUTH_WHITELIST

    static {
        AUTH_WHITELIST = [
                "/",
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/actuator/**"
        ]
    }

    static String[] getAUTH_WHITELIST() {
        return AUTH_WHITELIST
    }
}
