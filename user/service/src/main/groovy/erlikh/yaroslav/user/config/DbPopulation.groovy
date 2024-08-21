package erlikh.yaroslav.user.config

import erlikh.yaroslav.api.user.UserRole
import erlikh.yaroslav.user.entity.UserEntity
import erlikh.yaroslav.user.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.context.event.EventListener

@Component
class DbPopulation {

    @Autowired
    private UserRepository userRepository

    @Autowired
    private PasswordEncoder passwordEncoder

    @EventListener(ApplicationReadyEvent.class)
    void populate() {
        userRepository.saveAll(
                List.of(
                        new UserEntity(
                                login: "admin",
                                name: "Admin",
                                password: passwordEncoder.encode("12345"),
                                role: UserRole.ADMIN
                        ),
                        new UserEntity(
                                login: "user",
                                name: "User",
                                password: passwordEncoder.encode("123"),
                                role: UserRole.USER
                        )
                )
        )
    }
}
