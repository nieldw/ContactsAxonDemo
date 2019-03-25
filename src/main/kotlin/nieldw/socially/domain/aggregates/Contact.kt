package nieldw.socially.domain.aggregates

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.RelationshipLevel
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.commands.UpdateRelationshipLevelCommand
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.PlatformContactAddedEvent
import nieldw.socially.domain.events.RelationshipLevelUpdatedEvent
import nieldw.socially.domain.platform.PlatformContact
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.commandhandling.model.AggregateLifecycle.apply as applyEvent

@Aggregate
class Contact() {

    @AggregateIdentifier
    private lateinit var contactId: ContactId
    private lateinit var basicInfo: BasicInfo
    private lateinit var relationshipLevel: RelationshipLevel
    private val platformContacts = mutableListOf<PlatformContact>()

    @CommandHandler
    constructor(command: AddContactCommand) : this() {
        applyEvent(ContactAddedEvent(ContactId(), command.basicInfo))
        command.platformContacts.forEach { applyEvent(PlatformContactAddedEvent(it)) }
    }

    @EventSourcingHandler
    fun handle(event: ContactAddedEvent) {
        this.contactId = event.contactId
        this.basicInfo = event.basicInfo
    }

    @EventSourcingHandler
    fun handle(event: PlatformContactAddedEvent) {
        this.platformContacts.add(event.platformContact)
    }

    @CommandHandler
    fun handle(command: UpdateRelationshipLevelCommand): RelationshipLevel {
        applyEvent(RelationshipLevelUpdatedEvent(contactId, command.relationshipLevel))
        return command.relationshipLevel
    }

    @EventSourcingHandler
    fun handle(event: RelationshipLevelUpdatedEvent) {
        this.relationshipLevel = event.newRelationshipLevel
    }
}
