package by.bogdan.springbatch.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(@Id var id: Long,
                var name: String,
                var department: String,
                var salary: Long) {
    constructor() : this(0, "", "", 0)
}