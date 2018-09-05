package nieldw.socially.web.rest

import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName

data class ContactDTO(val firstName: String, val lastName: String) {
    fun getBasicInfo() = BasicInfo(FirstName(firstName), LastName(lastName))
}