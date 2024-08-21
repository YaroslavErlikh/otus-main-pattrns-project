package erlikh.yaroslav.user.mapper

import erlikh.yaroslav.api.user.UserDto
import erlikh.yaroslav.user.entity.UserEntity

class UserConverter {

    static UserDto toDto(UserEntity user) {
        return new UserDto(
                user.name,
                user.login,
                user.password,
                user.role
        )
    }
}
