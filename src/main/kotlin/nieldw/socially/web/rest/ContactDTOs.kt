package nieldw.socially.web.rest

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import java.util.*

data class ContactDTO(val firstName: String, val lastName: String) {
    fun getBasicInfo() = BasicInfo(FirstName(firstName), LastName(lastName))
}

data class ContactIdDTO(val id: UUID) {
    companion object {
        fun from(contactId: ContactId) = ContactIdDTO(contactId.toUUID())
    }
}