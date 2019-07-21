package nieldw.socially.domain.projections

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.queries.BasicInfoQuery
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class ContactBasicInfoQueryHandlerTest {
    private val repo = mockk<ContactBasicInfoRepo>()
    private val handler = ContactBasicInfoQueryHandler(repo)

    @BeforeEach
    fun `set up default mockk behaviour`() {
        every {
            @Suppress("RemoveExplicitTypeArguments")
            repo.save(any<BasicInfoProjection>())
        } answers { firstArg() }
    }

    @Test
    fun `handle ContactAddedEvent should save BasicInfoProjection`() {
        val firstName = "Piet"
        val lastName = "Bouwer"
        val id = UUID.randomUUID()

        val event = ContactAddedEvent(ContactId(id), BasicInfo(FirstName(firstName), LastName(lastName)))
        handler.handle(event)

        val expectedProjection = BasicInfoProjection(id, firstName, lastName)
        verify { repo.save(expectedProjection) }
    }

    @Test
    fun `findBasicInfo should return Optional_Empty if object does not exist`() {
        val id = UUID.randomUUID()
        every { repo.findById(id) } returns Optional.empty()

        val optional = handler.findBasicInfo(BasicInfoQuery(ContactId(id)))
        assertThat(optional).isEmpty
    }

    @Test
    fun `findBasicInfo should return BasicInfo if object exists`() {
        val id = UUID.randomUUID()
        val firstName = FirstName("Jaco")
        val lastName = LastName("Eden")
        every { repo.findById(id) } returns Optional.of(BasicInfoProjection(ContactId(id), firstName, lastName))

        val optional = handler.findBasicInfo(BasicInfoQuery(ContactId(id)))
        assertThat(optional).hasValue(BasicInfo(firstName, lastName))
    }
}