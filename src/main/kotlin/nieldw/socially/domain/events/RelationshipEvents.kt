package nieldw.socially.domain.events

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.RelationshipLevel

data class RelationshipLevelUpdatedEvent(
        val contactId: ContactId,
        val newRelationshipLevel: RelationshipLevel
)