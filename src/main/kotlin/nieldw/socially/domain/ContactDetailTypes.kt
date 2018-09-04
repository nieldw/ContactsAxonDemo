package nieldw.socially.domain

data class EmailAddress(private val value: String)
data class TelephoneNumber(private val value: String)

enum class RelationshipLevel {
    NONE, ACQUAINTANCE, ASSOCIATE, FRIEND
}