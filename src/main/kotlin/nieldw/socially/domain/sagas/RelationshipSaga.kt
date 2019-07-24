package nieldw.socially.domain.sagas

import nieldw.socially.domain.InteractionScore
import nieldw.socially.domain.RelationshipLevel
import nieldw.socially.domain.commands.UpdateRelationshipLevelCommand
import nieldw.socially.domain.events.InteractionAddedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile

@Profile("command")
@Saga
class RelationshipSaga {

    @Transient
    @Autowired
    private lateinit var commandGateway: CommandGateway

    private var relationshipInteractionScore = InteractionScore.NONE

    @StartSaga
    @SagaEventHandler(associationProperty = "contactId")
    fun handle(interactionAddedEvent: InteractionAddedEvent) {
        if (this.relationshipInteractionScore == InteractionScore.NONE) {
            this.commandGateway.send<UpdateRelationshipLevelCommand>(
                    UpdateRelationshipLevelCommand(interactionAddedEvent.contactId, RelationshipLevel.ACQUAINTANCE, interactionAddedEvent.interactionScore))
        }

        this.relationshipInteractionScore += interactionAddedEvent.interactionScore
        when {
            this.relationshipInteractionScore >= InteractionScore(10) ->
                this.commandGateway.send<UpdateRelationshipLevelCommand>(
                        UpdateRelationshipLevelCommand(interactionAddedEvent.contactId, RelationshipLevel.ASSOCIATE, this.relationshipInteractionScore))
        }
    }
}