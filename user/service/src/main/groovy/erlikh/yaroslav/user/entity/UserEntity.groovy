package erlikh.yaroslav.user.entity

import erlikh.yaroslav.api.user.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity extends AbstractEntity<Integer> implements Serializable {

    @Column(name = "name", nullable = true)
    String name = null

    @Column(name = "login", nullable = true)
    String login = null

    @Column(name = "password", nullable = true)
    String password = null

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = true)
    UserRole role = null
}
