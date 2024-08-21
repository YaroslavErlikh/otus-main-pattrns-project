package erlikh.yaroslav.user.repo

import erlikh.yaroslav.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String username)
}
