package by.bogdan.springbatch.batch

import by.bogdan.springbatch.enumerations.Departments
import by.bogdan.springbatch.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class CsvProcessor : ItemProcessor<User, User> {

    private val log: Logger = LoggerFactory.getLogger(CsvProcessor::class.java)

    override fun process(user: User?): User {
        val depCode = user!!.department
        val depName = Departments.getNameByCode(depCode)
        user.department = depName
        log.info("Converted from [$depCode] to [$depName]")
        return user
    }

}