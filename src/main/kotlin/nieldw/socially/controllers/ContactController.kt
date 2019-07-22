package nieldw.socially.controllers

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.projections.BasicInfoProjection
import nieldw.socially.domain.queries.BasicInfoQuery
import nieldw.socially.web.rest.ContactDTO
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component

@Component
class ContactController(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {
    fun getContactById(contactId: ContactId): BasicInfoProjection {
        return queryGateway.query(BasicInfoQuery(contactId), BasicInfoProjection::class.java).join()
    }

    fun addContact(contactDTO: ContactDTO): ContactId = commandGateway.sendAndWait(AddContactCommand(contactDTO.getBasicInfo()))
}