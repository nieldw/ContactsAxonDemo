package nieldw.socially.domain.projections

import nieldw.socially.domain.InteractionScore
import nieldw.socially.domain.RelationshipLevel
import nieldw.socially.domain.events.ContactAddedEvent
import nieldw.socially.domain.events.RelationshipLevelUpdatedEvent
import nieldw.socially.domain.queries.TopRelationshipBasicInfoQuery
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Profile("query")
internal interface ContactRelationshipLevelRepo : CrudRepository<ContactRelationshipLevelProjection, UUID> {
    fun findTopByOrderByInteractionScoreDesc(): Optional<ContactRelationshipLevelProjection>
}

@Profile("query")
@Repository
internal class TopRelationshipBasicInfoQueryHandler(private val repo: ContactRelationshipLevelRepo) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventHandler
    fun handle(event: ContactAddedEvent) {
        logger.debug("Contact relationship level initialised: $event")
        repo.save(ContactRelationshipLevelProjection(
                event.contactId.toUUID(),
                event.basicInfo.firstName.toString(),
                event.basicInfo.lastName.toString(),
                RelationshipLevel.NONE,
                InteractionScore.NONE.toLong()
        ))
    }

    @EventHandler
    fun handle(event: RelationshipLevelUpdatedEvent) {
        logger.debug("Contact relationship level updated: $event")
        val projection = repo.findById(event.contactId.toUUID()).orElseThrow { throw UnknownContactException("$event") }
        projection.relationshipLevel = event.newRelationshipLevel
        projection.interactionScore = event.newInteractionScore.toLong()
        repo.save(projection)
    }

    @QueryHandler
    fun findTopRelationship(query: TopRelationshipBasicInfoQuery): ContactRelationshipLevelProjection {
        return repo.findTopByOrderByInteractionScoreDesc()
                .orElseThrow { UnknownContactException("No contacts were found") }
    }
}