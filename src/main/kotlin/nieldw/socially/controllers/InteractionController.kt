package nieldw.socially.controllers

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.InteractionId
import nieldw.socially.domain.commands.AddInteractionCommand
import nieldw.socially.domain.platform.PlatformContact
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("rest")
@Component
class InteractionController(private val commandGateway: CommandGateway) {
    fun addInteraction(contactId: ContactId, platformContact: PlatformContact) {
        commandGateway.send<InteractionId>(AddInteractionCommand(contactId, platformContact))
    }
}
