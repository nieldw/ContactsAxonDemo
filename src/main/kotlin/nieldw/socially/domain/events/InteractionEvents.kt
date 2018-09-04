package nieldw.socially.domain.events

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.InteractionId
import nieldw.socially.domain.InteractionScore
import nieldw.socially.domain.platform.PlatformContact

data class InteractionAddedEvent(
        val interactionId: InteractionId,
        val contactId: ContactId,
        val platformContact: PlatformContact,
        val interactionScore: InteractionScore
)