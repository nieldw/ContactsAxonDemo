package nieldw.socially.domain.commands

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.RelationshipLevel
import nieldw.socially.domain.platform.PlatformContact
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class AddContactCommand(
    val basicInfo: BasicInfo,
    val platformContacts: List<PlatformContact> = emptyList()
)

data class UpdateRelationshipLevelCommand(
        @TargetAggregateIdentifier val contactId: ContactId,
        val relationshipLevel: RelationshipLevel
)
