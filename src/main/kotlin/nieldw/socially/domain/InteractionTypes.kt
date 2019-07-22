package nieldw.socially.domain

import java.util.*

data class InteractionId(private val id: UUID) {
    constructor() : this(UUID.randomUUID())
}

data class InteractionScore(private val score: Long) : Comparable<InteractionScore> {
    companion object {
        val NONE get() = InteractionScore(0)
    }

    operator fun plus(rhs: InteractionScore): InteractionScore = InteractionScore(this.score + rhs.score)

    override operator fun compareTo(other: InteractionScore): Int = this.score.compareTo(other.score)
    fun toLong() = score
}