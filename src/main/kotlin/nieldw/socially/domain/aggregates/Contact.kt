package nieldw.socially.domain.aggregates

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.PlatformContactAddedEvent
import nieldw.socially.domain.platform.PlatformContact
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Contact() {

    @AggregateIdentifier
    private lateinit var contactId: String
    private lateinit var basicInfo: BasicInfo
    private val platformContacts = mutableListOf<PlatformContact>()

    @CommandHandler
    constructor(command: AddContactCommand) : this() {
        apply(ContactAddedEvent(command.contactId, command.basicInfo))
        command.platformContacts.forEach { apply(PlatformContactAddedEvent(it)) }
    }

    @EventSourcingHandler
    fun handle(contactAddedEvent: ContactAddedEvent) {
        this.basicInfo = contactAddedEvent.basicInfo
    }

    @EventSourcingHandler
    fun handle(platformContactAddedEvent: PlatformContactAddedEvent) {
        this.platformContacts.add(platformContactAddedEvent.platformContact)
    }
}
