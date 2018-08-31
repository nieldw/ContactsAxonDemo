package nieldw.socially.domain.commands

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.platform.PlatformContact

data class AddContactCommand(
    val basicInfo: BasicInfo,
    val platformContacts: List<PlatformContact> = emptyList()
)
