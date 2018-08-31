package nieldw.socially.domain.platform

data class FacebookContact(val username: String, val firstName: String, val lastName: String) : PlatformContact {
    override fun platform() = PlatformType.FACEBOOK
}
