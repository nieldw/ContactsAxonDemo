package nieldw.socially.domain.sagas

import nieldw.socially.domain.*
import nieldw.socially.domain.commands.UpdateRelationshipLevelCommand
import nieldw.socially.domain.events.InteractionAddedEvent
import nieldw.socially.domain.platform.EmailContact
import nieldw.socially.domain.platform.TelephoneContact
import org.axonframework.test.saga.SagaTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RelationshipSagaTest {

    private lateinit var fixture: SagaTestFixture<RelationshipSaga>

    @BeforeEach
    fun `reset saga test fixture`() {
        fixture = SagaTestFixture(RelationshipSaga::class.java)
    }

    @Test
    fun `should update relationship level to acquaintance after first interaction`() {
        val contactId = ContactId()
        val aragornEmail = EmailContact(EmailAddress("aragorn@rangers.nth"))
        fixture
                .givenNoPriorActivity()
                .whenPublishingA(InteractionAddedEvent(InteractionId(), contactId, aragornEmail, InteractionScore(3)))
                .expectActiveSagas(1)
                .expectDispatchedCommands(UpdateRelationshipLevelCommand(contactId, RelationshipLevel.ACQUAINTANCE))
    }

    @Test
    fun `should add up interaction scores from events and issue UpdateRelationshipLevelCommand when score exceeds 10`() {
        val contactId = ContactId()
        val aragornEmail = EmailContact(EmailAddress("aragorn@rangers.nth"))
        val aragornTelephone = TelephoneContact(TelephoneNumber("911-ARAGORN"))
        fixture
                .givenAPublished(InteractionAddedEvent(InteractionId(), contactId, aragornEmail, InteractionScore(3)))
                .andThenAPublished(InteractionAddedEvent(InteractionId(), contactId, aragornEmail, InteractionScore(3)))
                .whenPublishingA(InteractionAddedEvent(InteractionId(), contactId, aragornTelephone, InteractionScore(5)))
                .expectActiveSagas(1)
                .expectDispatchedCommands(UpdateRelationshipLevelCommand(contactId, RelationshipLevel.ASSOCIATE))
    }
}
