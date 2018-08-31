package nieldw.socially.domain

data class ContactId(private val id: String)

data class FirstName(private val name: String)
data class LastName(private val name: String)
data class BasicInfo(private val firstName: FirstName, private val lastName: LastName)
