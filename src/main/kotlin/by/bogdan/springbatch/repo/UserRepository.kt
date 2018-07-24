package by.bogdan.springbatch.repo

import by.bogdan.springbatch.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}