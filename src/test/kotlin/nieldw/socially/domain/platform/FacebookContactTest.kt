package nieldw.socially.domain.platform

import com.shazam.shazamcrest.MatcherAssert.assertThat
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import nieldw.socially.domain.Username
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

internal class FacebookContactTest {

    @Test
    fun platform() {
        val facebookContact = FacebookContact(
                Username("maryadoc.b"),
                FirstName("Maryadoc"),
                LastName("Brandybuck")
        )
        assertThat(facebookContact.platform(), equalTo(PlatformType.FACEBOOK))
    }
}