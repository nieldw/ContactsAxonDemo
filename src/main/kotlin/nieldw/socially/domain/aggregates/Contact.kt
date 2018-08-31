package nieldw.socially.domain.aggregates

import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.PlatformContactAddedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Contact() {

    @AggregateIdentifier
    private lateinit var contactId: String

    @CommandHandler
    constructor(command: AddContactCommand) : this() {
        apply(ContactAddedEvent(command.contactId, command.basicInfo))
        command.platformContacts.forEach { apply(PlatformContactAddedEvent(it)) }
    }
}
