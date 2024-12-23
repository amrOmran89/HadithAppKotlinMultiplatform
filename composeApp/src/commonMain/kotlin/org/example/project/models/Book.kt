package org.example.project.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val bookName: String,
    val bookSlug: String,
    @SerialName("chapters_count") val chaptersCount: String,
    @SerialName("hadiths_count")  val hadithsCount: String,
    val id: Int,
    val writerDeath: String,
    val writerName: String
)