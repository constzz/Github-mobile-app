package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepo(
    @SerialName("full_name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("updated_at")
    val lastUpdate: String,
    @SerialName("stargazers_count")
    val numberOfStars: Int
)
