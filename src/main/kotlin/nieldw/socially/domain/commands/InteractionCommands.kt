package nieldw.socially.domain.commands

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.InteractionId
import nieldw.socially.domain.platform.PlatformContact
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AddInteractionCommand(val contactId: ContactId, val platformContact: PlatformContact) {
    @TargetAggregateIdentifier
    val interactionId: InteractionId = InteractionId()
}