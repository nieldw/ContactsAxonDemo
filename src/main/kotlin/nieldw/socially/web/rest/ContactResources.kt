package nieldw.socially.web.rest

import nieldw.socially.controllers.ContactController
import nieldw.socially.domain.ContactId
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
internal class ContactResources(private val contactController: ContactController) {

    @GetMapping("/contact/{id}")
    fun getContact(@PathVariable id: UUID) = contactController.getContactById(ContactId(id))

    @PostMapping("/contact", consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun addContact(@RequestBody dto: ContactDTO): ContactIdDTO = ContactIdDTO.from(contactController.addContact(dto))
}