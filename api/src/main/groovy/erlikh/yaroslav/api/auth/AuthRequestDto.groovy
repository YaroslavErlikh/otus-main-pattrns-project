package erlikh.yaroslav.api.auth

record AuthRequestDto (
    private final String login,
    private final String password
) {}