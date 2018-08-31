package nieldw.socially.domain.events

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.ContactId
import nieldw.socially.domain.platform.PlatformContact

data class ContactAddedEvent(val contactId: ContactId, val basicInfo: BasicInfo)

data class PlatformContactAddedEvent(val platformContact: PlatformContact)
