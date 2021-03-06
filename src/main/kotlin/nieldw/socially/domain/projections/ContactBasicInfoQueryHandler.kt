package nieldw.socially.domain.projections

import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.queries.BasicInfoQuery
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Profile("query")
internal interface ContactBasicInfoRepo : CrudRepository<BasicInfoProjection, UUID>

@Profile("query")
@Repository
internal class ContactBasicInfoQueryHandler(private val repo: ContactBasicInfoRepo) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventHandler
    fun handle(event: ContactAddedEvent) {
        logger.debug("Contact added: $event")
        repo.save(BasicInfoProjection(event.contactId, event.basicInfo.firstName, event.basicInfo.lastName))
    }

    @QueryHandler
    fun findBasicInfo(query: BasicInfoQuery): BasicInfoProjection {
        return repo.findById(query.contactId.toUUID())
                .orElseThrow { UnknownContactException("ContactId ${query.contactId} not recognised") }
    }
}