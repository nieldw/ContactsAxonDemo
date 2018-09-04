package nieldw.socially.domain.commands

import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import io.mockk.every
import io.mockk.mockk
import nieldw.socially.domain.*
import nieldw.socially.domain.aggregates.Interaction
import nieldw.socially.domain.events.InteractionAddedEvent
import nieldw.socially.domain.platform.FacebookContact
import nieldw.socially.domain.services.InteractionScoreCalculator
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.matchers.Matchers.exactSequenceOf
import org.axonframework.test.matchers.Matchers.payloadsMatching
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddInteractionCommandTest {

    private lateinit var fixture: AggregateTestFixture<Interaction>

    @BeforeEach
    fun `reset aggregate test fixture`() {
        fixture = AggregateTestFixture(Interaction::class.java)
    }

    @Test
    fun `it should create and calculate the interaction score`() {
        val contactId = ContactId()
        val basicInfo = BasicInfo(FirstName("Samwise"), LastName("Gamgee"))
        val facebookContact = FacebookContact(Username("sam.gee"), basicInfo)
        val validInteractionId = Matchers.instanceOf<InteractionId>(InteractionId::class.java)

        val calculator: InteractionScoreCalculator = mockk()
        val expectedScore = InteractionScore(4)
        every { calculator.calculateScore(any()) } returns expectedScore

        fixture
                .registerInjectableResource(calculator)
                .givenNoPriorActivity()
                .`when`(AddInteractionCommand(contactId, facebookContact))
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        sameBeanAs(InteractionAddedEvent(InteractionId(), contactId, facebookContact, expectedScore))
                                .with("interactionId", validInteractionId))))
                .expectReturnValueMatching(validInteractionId)
    }
}
