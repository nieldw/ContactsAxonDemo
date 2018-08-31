package nieldw.socially.domain.platform

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import nieldw.socially.domain.Username

data class FacebookContact(
        private val username: Username,
        private val firstName: FirstName,
        private val lastName: LastName) : PlatformContact {

    constructor(username: Username, basicInfo: BasicInfo) : this(username, basicInfo.firstName, basicInfo.lastName)

    override fun platform() = PlatformType.FACEBOOK
}
