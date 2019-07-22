package nieldw.socially.domain.commands

import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import nieldw.socially.domain.*
import nieldw.socially.domain.aggregates.Contact
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.PlatformContactAddedEvent
import nieldw.socially.domain.platform.EmailContact
import nieldw.socially.domain.platform.FacebookContact
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.matchers.Matchers.*
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
        val addContactCommand = AddContactCommand(basicInfo)
        val expectedEvent = ContactAddedEvent(addContactCommand.contactId, basicInfo)

        fixture.givenNoPriorActivity()
                .`when`(addContactCommand)
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        sameBeanAs(expectedEvent))))
                .expectResultMessagePayload(addContactCommand.contactId)
    }

    @Test
    fun `it should add all platform contacts`() {
        val basicInfo = BasicInfo(FirstName("Samwise"), LastName("Gamgee"))
        val facebookContact = FacebookContact(Username("sam.gee"), basicInfo)
        val emailContact = EmailContact(EmailAddress("sam@bagend.me"))
        val addContactCommand = AddContactCommand(basicInfo, listOf(facebookContact, emailContact))
        val expectedContactAddedEvent = ContactAddedEvent(addContactCommand.contactId, basicInfo)
        val firstExpectedPlatformContactAddedEvent = PlatformContactAddedEvent(facebookContact)
        val secondExpectedPlatformContactAddedEvent = PlatformContactAddedEvent(emailContact)

        fixture.givenNoPriorActivity()
                .`when`(addContactCommand)
                .expectEventsMatching(listWithAllOf(
                        messageWithPayload(sameBeanAs(expectedContactAddedEvent))))
                .expectEventsMatching(listWithAllOf(
                        messageWithPayload(sameBeanAs(firstExpectedPlatformContactAddedEvent)),
                        messageWithPayload(sameBeanAs(secondExpectedPlatformContactAddedEvent))))
                .expectResultMessagePayload(addContactCommand.contactId)
    }
}
