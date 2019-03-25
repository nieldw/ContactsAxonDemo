package nieldw.socially.domain.commands

import nieldw.socially.domain.*
import nieldw.socially.domain.aggregates.Contact
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.RelationshipLevelUpdatedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UpdateRelationshipLevelCommandTest {

    private lateinit var fixture: AggregateTestFixture<Contact>

    @BeforeEach
    fun `reset aggregate test fixture`() {
        fixture = AggregateTestFixture(Contact::class.java)
    }

    @Test
    fun `it should update contact relationship level`() {
        val basicInfo = BasicInfo(FirstName("Samwise"), LastName("Gamgee"))
        val contactId = ContactId()
        val expectedEvent = RelationshipLevelUpdatedEvent(contactId, RelationshipLevel.ASSOCIATE)

        fixture
                .givenNoPriorActivity()
                .andGiven(ContactAddedEvent(contactId, basicInfo))
                .`when`(UpdateRelationshipLevelCommand(contactId, RelationshipLevel.ASSOCIATE))
                .expectEvents(expectedEvent)
                .expectReturnValue(RelationshipLevel.ASSOCIATE)
    }
}