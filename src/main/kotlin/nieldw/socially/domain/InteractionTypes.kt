package nieldw.socially.domain

import java.util.*

data class InteractionId(private val id: UUID) {
    constructor(): this(UUID.randomUUID())
}

data class InteractionScore(private val score: Long)