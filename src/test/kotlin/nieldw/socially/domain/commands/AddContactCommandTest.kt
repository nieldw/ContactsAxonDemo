package nieldw.socially.domain.commands

import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import nieldw.socially.domain.aggregates.Contact
import nieldw.socially.domain.events.ContactAddedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.matchers.Matchers.exactSequenceOf
import org.axonframework.test.matchers.Matchers.payloadsMatching
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddContactCommandTest {

    private lateinit var fixture: AggregateTestFixture<Contact>

    @BeforeEach
    fun `reset aggregate test fixture`() {
        fixture = AggregateTestFixture(Contact::class.java)
    }

    @Test
    fun `it should add basic info`() {
        val basicInfo = BasicInfo(FirstName("Samwise"), LastName("Gamgee"))
        val expectedEvent = ContactAddedEvent(ContactId(), basicInfo)
        val validContactId = Matchers.instanceOf<ContactId>(ContactId::class.java)

        fixture.givenNoPriorActivity()
                .`when`(AddContactCommand(basicInfo))
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        sameBeanAs(expectedEvent)
                                .with("contactId", validContactId))))
                .expectReturnValueMatching(validContactId)
    }
}
