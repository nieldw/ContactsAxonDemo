package nieldw.socially.controllers

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.projections.BasicInfoProjection
import nieldw.socially.domain.projections.ContactRelationshipLevelProjection
import nieldw.socially.domain.queries.BasicInfoQuery
import nieldw.socially.domain.queries.TopRelationshipBasicInfoQuery
import nieldw.socially.web.rest.ContactDTO
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("rest")
@Component
class ContactController(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {
    fun getContactById(contactId: ContactId): BasicInfoProjection =
            queryGateway.query(BasicInfoQuery(contactId), BasicInfoProjection::class.java).join()

    fun getTopRelationship(): ContactRelationshipLevelProjection =
            queryGateway.query(TopRelationshipBasicInfoQuery(), ContactRelationshipLevelProjection::class.java).join()

    fun addContact(contactDTO: ContactDTO): ContactId =
            commandGateway.sendAndWait(AddContactCommand(contactDTO.getBasicInfo()))
}