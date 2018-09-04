package nieldw.socially.domain.commands

import nieldw.socially.domain.ContactId
import nieldw.socially.domain.platform.PlatformContact

data class AddInteractionCommand(val contactId: ContactId, val platformContact: PlatformContact)