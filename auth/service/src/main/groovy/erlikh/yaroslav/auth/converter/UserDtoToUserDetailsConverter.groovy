package erlikh.yaroslav.auth.converter

import erlikh.yaroslav.api.user.UserDto
import erlikh.yaroslav.auth.details.AuthUserDetails
import org.springframework.security.core.userdetails.UserDetails

class UserDtoToUserDetailsConverter {

    static UserDetails dtoToUserDetails(UserDto userDto) {
        return new AuthUserDetails(
                userDto.name(),
                userDto.login(),
                userDto.password(),
                userDto.role()
        )
    }
}