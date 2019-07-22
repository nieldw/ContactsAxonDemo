package nieldw.socially.domain.projections

import nieldw.socially.domain.*
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.RelationshipLevelUpdatedEvent
import nieldw.socially.domain.queries.TopRelationshipBasicInfoQuery
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class TopRelationshipBasicInfoQueryHandlerTest {
    @Autowired
    private lateinit var repo: ContactRelationshipLevelRepo
    private val handler by lazy { TopRelationshipBasicInfoQueryHandler(repo) }

    @Test
    fun `a new contact has no interaction score`() {
        val event = ContactAddedEvent(ContactId(), BasicInfo(FirstName("Aragorn"), LastName("Elessar")))
        handler.handle(event)
        val optional = handler.findTopRelationship(TopRelationshipBasicInfoQuery())

        assertThat(optional).isNotEmpty
        assertThat(optional).hasValue(ContactRelationshipLevelProjection(
                event.contactId.toUUID(),
                event.basicInfo.firstName.toString(),
                event.basicInfo.lastName.toString(),
                RelationshipLevel.NONE,
                InteractionScore.NONE.toLong()
        ))
    }

    @Test
    fun `the contact with the highest current interaction score is returned`() {
        val expectedContactId = ContactId()
        val expectedBasicInfo = BasicInfo(FirstName("Aragorn"), LastName("Elessar"))
        handler.handle(ContactAddedEvent(expectedContactId, expectedBasicInfo))
        handler.handle(ContactAddedEvent(ContactId(), BasicInfo(FirstName("Durin"), LastName("Deathless"))))
        handler.handle(RelationshipLevelUpdatedEvent(expectedContactId, RelationshipLevel.ACQUAINTANCE, InteractionScore(5)))

        val optional = handler.findTopRelationship(TopRelationshipBasicInfoQuery())
        assertThat(optional).hasValue(ContactRelationshipLevelProjection(
                expectedContactId.toUUID(),
                expectedBasicInfo.firstName.toString(),
                expectedBasicInfo.lastName.toString(),
                RelationshipLevel.ACQUAINTANCE,
                5
        ))
    }
}