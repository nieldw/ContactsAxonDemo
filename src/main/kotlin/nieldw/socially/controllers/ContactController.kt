package nieldw.socially.controllers

import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.web.rest.ContactDTO
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Component

@Component
class ContactController(private val commandGateway: CommandGateway) {
    fun addContact(contactDTO: ContactDTO) {
        commandGateway.sendAndWait<AddContactCommand>(AddContactCommand(contactDTO.getBasicInfo()))
    }
}