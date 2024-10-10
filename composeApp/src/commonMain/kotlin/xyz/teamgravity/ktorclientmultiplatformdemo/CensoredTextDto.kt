package xyz.teamgravity.ktorclientmultiplatformdemo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CensoredTextDto(
    @SerialName("result") val result: String
)
