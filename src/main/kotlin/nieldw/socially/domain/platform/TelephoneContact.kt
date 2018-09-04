package nieldw.socially.domain.platform

import nieldw.socially.domain.TelephoneNumber

data class TelephoneContact(private val telephoneNumber: TelephoneNumber) : PlatformContact {
    override fun platform() = PlatformType.TELEPHONE
}
