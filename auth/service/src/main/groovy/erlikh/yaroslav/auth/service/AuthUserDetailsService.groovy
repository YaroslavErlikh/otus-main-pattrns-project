package erlikh.yaroslav.auth.service

import erlikh.yaroslav.auth.converter.UserDtoToUserDetailsConverter
import erlikh.yaroslav.user.client.UserClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient

    @Override
    UserDetails loadUserByUsername(String username) {
        return UserDtoToUserDetailsConverter.dtoToUserDetails(userClient.findByLogin(username))
    }
}
