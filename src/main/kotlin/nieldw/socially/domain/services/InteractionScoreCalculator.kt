package nieldw.socially.domain.services

import nieldw.socially.domain.InteractionScore
import nieldw.socially.domain.UnknownPlatformException
import nieldw.socially.domain.platform.PlatformContact
import nieldw.socially.domain.platform.PlatformType
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("command")
@Component
class InteractionScoreCalculator {
    private val scoreMap = mapOf(
            PlatformType.FACEBOOK to InteractionScore(1),
            PlatformType.EMAIL to InteractionScore(3),
            PlatformType.TELEPHONE to InteractionScore(5)
    )

    fun calculateScore(platformContact: PlatformContact) =
            scoreMap[platformContact.platform()]
                    ?: throw UnknownPlatformException("Platform ${platformContact.platform()} does not have a score")
}