package nieldw.socially.domain

import java.util.*

data class ContactId(private val id: UUID) {
    constructor() : this(UUID.randomUUID())
}

data class FirstName(private val name: String)
data class LastName(private val name: String)
data class BasicInfo(private val firstName: FirstName, private val lastName: LastName)
