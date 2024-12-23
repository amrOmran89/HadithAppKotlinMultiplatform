package org.example.project.models

import kotlinx.serialization.Serializable

@Serializable
data class Chapters(
    val chapters: List<Chapter>,
    val message: String,
    val status: Int
)

@Serializable
data class Chapter(
    val bookSlug: String,
    val chapterArabic: String,
    val chapterEnglish: String,
    val chapterNumber: String,
    val chapterUrdu: String,
    val id: Int
)