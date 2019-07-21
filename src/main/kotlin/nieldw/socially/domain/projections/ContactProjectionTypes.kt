package nieldw.socially.domain.projections

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BasicInfoProjection(
        @Id
        val contactId: UUID,
        val firstName: String,
        val lastName: String) {
    constructor(contactId: ContactId, firstName: FirstName, lastName: LastName) : this(contactId.toUUID(), firstName.toString(), lastName.toString())
}
