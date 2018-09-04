package nieldw.socially.domain.services

import com.shazam.shazamcrest.MatcherAssert.assertThat
import nieldw.socially.domain.InteractionScore
import nieldw.socially.domain.platform.PlatformContact
import nieldw.socially.domain.platform.PlatformType.*
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class InteractionScoreCalculatorTest {

    private val calculator = InteractionScoreCalculator()

    @TestFactory
    fun calculateScore() = listOf(
            FACEBOOK to InteractionScore(1),
            EMAIL to InteractionScore(3),
            TELEPHONE to InteractionScore(5))
            .map { (type, expectScore) ->
                dynamicTest("The interaction score of $type should be $expectScore") {
                    assertThat(calculator.calculateScore(object : PlatformContact {
                        override fun platform() = type
                    }), equalTo(expectScore))
                }
            }
}