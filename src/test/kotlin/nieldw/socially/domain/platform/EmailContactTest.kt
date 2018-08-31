package nieldw.socially.domain.platform

import com.shazam.shazamcrest.MatcherAssert.assertThat
import nieldw.socially.domain.EmailAddress
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

internal class EmailContactTest {

    @Test
    fun `platform should be EMAIL`() {
        val emailContact = EmailContact(EmailAddress("bilbo@bagend.me"))
        assertThat(emailContact.platform(), equalTo(PlatformType.EMAIL))
    }
}