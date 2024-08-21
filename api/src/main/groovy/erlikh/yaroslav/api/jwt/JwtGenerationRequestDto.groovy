package erlikh.yaroslav.api.jwt

record JwtGenerationRequestDto(
        String username,
        String authority
) {}