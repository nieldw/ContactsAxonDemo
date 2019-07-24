package nieldw.socially.web.rest

import nieldw.socially.controllers.InteractionController
import nieldw.socially.domain.ContactId
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Profile("rest")
@RestController
internal class InteractionResources(private val interactionController: InteractionController) {

    @PostMapping("/contact/{id}/interaction/telephone", consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun addTelephoneInteraction(@PathVariable id: UUID, @RequestBody dto: TelephoneContactDTO) =
            interactionController.addInteraction(ContactId(id), dto.getTelephoneContact())

    @PostMapping("/contact/{id}/interaction/email", consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun addEmailInteraction(@PathVariable id: UUID, @RequestBody dto: EmailContactDTO) =
            interactionController.addInteraction(ContactId(id), dto.getEmailContact())

    @PostMapping("/contact/{id}/interaction/facebook", consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun addFacebookInteraction(@PathVariable id: UUID, @RequestBody dto: FacebookContactDTO) =
            interactionController.addInteraction(ContactId(id), dto.getFacebookContact())
}