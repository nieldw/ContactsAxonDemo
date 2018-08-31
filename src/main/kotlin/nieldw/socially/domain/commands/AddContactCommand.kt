package nieldw.socially.domain.commands

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.platform.PlatformContact
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class AddContactCommand(
    @TargetAggregateIdentifier val contactId: ContactId,
    val basicInfo: BasicInfo,
    val platformContacts: List<PlatformContact>
)
