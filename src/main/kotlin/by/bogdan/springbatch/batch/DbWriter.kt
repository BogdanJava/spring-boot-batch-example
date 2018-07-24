package by.bogdan.springbatch.batch

import by.bogdan.springbatch.model.User
import by.bogdan.springbatch.repo.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class DbWriter(val userRepository: UserRepository) : ItemWriter<User> {

    private val log: Logger = LoggerFactory.getLogger(DbWriter::class.java)

    override fun write(items: MutableList<out User>?) {
        log.info("Data saved for users: $items")
        userRepository.save(items)
    }
}