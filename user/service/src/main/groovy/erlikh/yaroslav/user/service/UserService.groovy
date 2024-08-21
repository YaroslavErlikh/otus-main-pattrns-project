package erlikh.yaroslav.user.service

import erlikh.yaroslav.api.user.UserDto
import erlikh.yaroslav.user.exceptions.UserNotFoundException
import erlikh.yaroslav.user.mapper.UserConverter
import erlikh.yaroslav.user.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private UserRepository userRepository

    UserDto findByLogin(String login) {
        def user = userRepository.findByLogin(login)
        if (user != null) {
            return UserConverter.toDto(user)
        } else {
            throw new UserNotFoundException("User not found")
        }
    }
}
