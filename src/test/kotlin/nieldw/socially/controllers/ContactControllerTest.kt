package nieldw.socially.controllers

import com.shazam.shazamcrest.MatcherAssert.assertThat
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import nieldw.socially.domain.BasicInfo
import nieldw.socially.domain.FirstName
import nieldw.socially.domain.LastName
import nieldw.socially.domain.commands.AddContactCommand
import nieldw.socially.web.rest.ContactDTO
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.common.Registration
import org.axonframework.messaging.MessageDispatchInterceptor
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

internal class ContactControllerTest {

    @Test
    fun `addContact should pass a correctly formed command to the gateway`() {
        var actualCommand: AddContactCommand? = null
        val contactController = buildContactControllerSUT { command -> actualCommand = command as AddContactCommand }
        contactController.addContact(ContactDTO("Peregrin", "Took"))
        assertThat(actualCommand,
                `is`(sameBeanAs(AddContactCommand(BasicInfo(FirstName("Peregrin"), LastName("Took"))))))
    }

    private fun buildContactControllerSUT(capturingFunction: (command: Any) -> Unit): ContactController {
        val commandGateway = object : CommandGateway {
            override fun <R : Any?> sendAndWait(command: Any?): R {
                capturingFunction(command ?: fail { "Command should not be null" })
                return Any() as R
            }

            override fun <R : Any?> sendAndWait(command: Any?, timeout: Long, unit: TimeUnit?): R {
                throw UnsupportedOperationException("not implemented")
            }

            override fun <C : Any?, R : Any?> send(command: C, callback: CommandCallback<in C, in R>?) {
                throw UnsupportedOperationException("not implemented")
            }

            override fun <R : Any?> send(command: Any?): CompletableFuture<R> {
                throw UnsupportedOperationException("not implemented")
            }

            override fun registerDispatchInterceptor(interceptor: MessageDispatchInterceptor<in CommandMessage<*>>?): Registration {
                throw UnsupportedOperationException("not implemented")
            }
        }
        return ContactController(commandGateway)
    }
}