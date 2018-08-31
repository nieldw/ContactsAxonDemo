package nieldw.socially.domain.platform

import nieldw.socially.domain.EmailAddress

data class EmailContact(private val emailAddress: EmailAddress) : PlatformContact {
    override fun platform() = PlatformType.EMAIL
}
