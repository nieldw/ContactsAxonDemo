package nieldw.socially.web.rest

import nieldw.socially.domain.*
import nieldw.socially.domain.platform.EmailContact
import nieldw.socially.domain.platform.FacebookContact
import nieldw.socially.domain.platform.TelephoneContact

data class TelephoneContactDTO(val telephoneNumber: String) {
    fun getTelephoneContact() = TelephoneContact(TelephoneNumber(telephoneNumber))
}

data class EmailContactDTO(val emailAddress: String) {
    fun getEmailContact() = EmailContact(EmailAddress(emailAddress))
}

data class FacebookContactDTO(private val username: String,
                              private val firstName: String,
                              private val lastName: String) {
    fun getFacebookContact() = FacebookContact(Username(username), FirstName(firstName), LastName(lastName))
}
