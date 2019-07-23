package nieldw.socially.domain.aggregates

import nieldw.socially.domain.InteractionId
import nieldw.socially.domain.commands.AddInteractionCommand
import nieldw.socially.domain.events.InteractionAddedEvent
import nieldw.socially.domain.services.InteractionScoreCalculator
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateLifecycle.apply as applyEvent

@Aggregate
class Interaction() {

    @AggregateIdentifier
    private lateinit var interactionId: InteractionId

    @CommandHandler
    constructor(addInteractionCommand: AddInteractionCommand, interactionScoreCalculator: InteractionScoreCalculator) : this() {
        applyEvent(InteractionAddedEvent(
                addInteractionCommand.interactionId,
                addInteractionCommand.contactId,
                addInteractionCommand.platformContact,
                interactionScoreCalculator.calculateScore(addInteractionCommand.platformContact)
        ))
    }

    @EventSourcingHandler
    fun handle(interactionAddedEvent: InteractionAddedEvent) {
        this.interactionId = interactionAddedEvent.interactionId
    }
}