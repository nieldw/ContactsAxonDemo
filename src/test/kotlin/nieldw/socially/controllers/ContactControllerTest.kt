package nieldw.socially.controllers

import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import nieldw.socially.domain.*
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.projections.BasicInfoProjection
import nieldw.socially.domain.projections.ContactRelationshipLevelProjection
import nieldw.socially.domain.queries.BasicInfoQuery
import nieldw.socially.domain.queries.TopRelationshipBasicInfoQuery
import nieldw.socially.web.rest.ContactDTO
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.CompletableFuture.completedFuture

internal class ContactControllerTest {
    private val commandGateway = mockk<CommandGateway>()
    private val queryGateway = mockk<QueryGateway>()
    private val contactController = ContactController(commandGateway, queryGateway)

    @Test
    fun `addContact should pass a correctly formed command to the gateway`() {
        val expectedNewContactId = ContactId()
        every { commandGateway.sendAndWait<ContactId>(any<AddContactCommand>()) } returns expectedNewContactId

        val actualContactId = contactController.addContact(ContactDTO("Peregrin", "Took"))

        val expectedCommand = sameBeanAs(
                AddContactCommand(BasicInfo(FirstName("Peregrin"), LastName("Took"))))
                .ignoring("contactId")::matches
        verify { commandGateway.sendAndWait(match(expectedCommand)) }
        assertThat(actualContactId).isEqualTo(expectedNewContactId)
    }

    @Test
    fun `getByContactId should return query result`() {
        val contactId = ContactId()
        val expectedBasicInfoProjection = BasicInfoProjection(contactId, FirstName("Dain"), LastName("Ironfoot"))

        every { queryGateway.query(any<BasicInfoQuery>(), any<Class<BasicInfoProjection>>()) } returns completedFuture(expectedBasicInfoProjection)

        val basicInfoProjection = contactController.getContactById(contactId)
        assertThat(basicInfoProjection).isEqualTo(expectedBasicInfoProjection)
    }

    @Test
    fun `getTopRelationship should return query result`() {
        val contactId = UUID.randomUUID()
        val expectedTopRelationship = ContactRelationshipLevelProjection(contactId, "Dain", "Ironfoot", RelationshipLevel.FRIEND, 15)

        every { queryGateway.query(any<TopRelationshipBasicInfoQuery>(), any<Class<ContactRelationshipLevelProjection>>()) } returns completedFuture(expectedTopRelationship)

        val topRelationshipProjection = contactController.getTopRelationship()
        assertThat(topRelationshipProjection).isEqualTo(expectedTopRelationship)
    }
}