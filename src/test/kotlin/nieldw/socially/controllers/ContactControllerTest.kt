package nieldw.socially.controllers

import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.domain.projections.BasicInfoProjection
import nieldw.socially.domain.queries.BasicInfoQuery
import nieldw.socially.web.rest.ContactDTO
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
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

        val expectedCommand = sameBeanAs(AddContactCommand(BasicInfo(FirstName("Peregrin"), LastName("Took"))))::matches
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
}