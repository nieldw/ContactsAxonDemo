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
import kotlin.test.assertFailsWith

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
        val firstName = "Vidugavia"
        val lastName = "Rhovanion"
        val id = UUID.randomUUID()

        val event = ContactAddedEvent(ContactId(id), BasicInfo(FirstName(firstName), LastName(lastName)))
        handler.handle(event)

        val expectedProjection = BasicInfoProjection(id, firstName, lastName)
        verify { repo.save(expectedProjection) }
    }

    @Test
    fun `findBasicInfo should throw exception if object does not exist`() {
        val id = UUID.randomUUID()
        every { repo.findById(id) } returns Optional.empty()

        assertFailsWith<UnknownContactException> {
            handler.findBasicInfo(BasicInfoQuery(ContactId(id)))
        }
    }

    @Test
    fun `findBasicInfo should return BasicInfoProjection if object exists`() {
        val id = UUID.randomUUID()
        val firstName = FirstName("Vidumavi")
        val lastName = LastName("Rhovanion")
        every { repo.findById(id) } returns Optional.of(BasicInfoProjection(ContactId(id), firstName, lastName))

        val optional = handler.findBasicInfo(BasicInfoQuery(ContactId(id)))
        assertThat(optional).isEqualTo(BasicInfoProjection(ContactId(id), firstName, lastName))
    }
}