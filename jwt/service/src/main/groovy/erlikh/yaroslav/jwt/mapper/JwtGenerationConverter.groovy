package erlikh.yaroslav.jwt.mapper

import erlikh.yaroslav.api.jwt.JwtGenerationRequestDto
import erlikh.yaroslav.api.user.User
import erlikh.yaroslav.api.user.UserAuthority
import erlikh.yaroslav.api.user.Username

class JwtGenerationConverter {

    static toUser(JwtGenerationRequestDto dto) {
        new User(new Username(dto.username()), new UserAuthority(dto.authority()))
    }
}
