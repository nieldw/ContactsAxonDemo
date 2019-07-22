package nieldw.socially.domain

import java.util.*

data class ContactId(private val id: UUID) {
    constructor() : this(UUID.randomUUID())

    fun toUUID() = id
}

data class Username(private val name: String)

data class FirstName(private val name: String) {
    override fun toString() = name
}

data class LastName(private val name: String) {
    override fun toString() = name
}

data class BasicInfo(val firstName: FirstName, val lastName: LastName)
