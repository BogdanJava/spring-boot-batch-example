package by.bogdan.springbatch.enumerations

import java.util.*

enum class Departments(val code: String,
                       val depName: String) {
    DEVELOPMENT(code = "001", depName = "Development"),
    DESIGN(code = "002", depName = "Design"),
    ACCOUNTING(code = "004", depName = "Accounting"),
    MANAGEMENT(code = "005", depName = "Management"),
    MARKETING(code = "003", depName = "Marketing"),
    DEFAULT(code = "000", depName = "Undefined");

    companion object {
        fun getNameByCode(code: String) = Arrays.stream(Departments.values())
                .filter { dep -> dep.code == code }
                .map { dep -> dep.depName }
                .findFirst().orElseGet { DEFAULT.name }

        fun getCodeByName(name: String) = Arrays.stream(Departments.values())
                .filter { dep -> dep.name == name }
                .map { dep -> dep.code }
                .findFirst().orElseGet { DEFAULT.code }
    }
}