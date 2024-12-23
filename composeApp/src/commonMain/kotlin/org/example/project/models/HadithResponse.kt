package org.example.project.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HadithResponse(
    val status: Int,
    val message: String,
    val hadiths: Hadith
)

@Serializable
data class Hadith(
    @SerialName("current_page") val currentPage: Int,
    val data: List<HadithData>,
    @SerialName("next_page_url") val nextPageUrl: String? = null
)

@Serializable
data class HadithData(
    val id: Int,
    @SerialName("hadith_number") val hadithNumber: String,
    @SerialName("english_narrator") val englishNarrator: String? = null,
    @SerialName("hadith_english") val hadithEnglish: String? = null,
    @SerialName("hadith_urdu") val hadithUrdu: String? = null,
    @SerialName("urdu_narrator") val urduNarrator: String? = null,
    @SerialName("hadith_arabic") val hadithArabic: String,
    @SerialName("heading_arabic") val headingArabic: String? = null,
    @SerialName("heading_urdu") val headingUrdu: String? = null,
    @SerialName("heading_english") val headingEnglish: String? = null,
    @SerialName("chapter_id") val chapterId: String,
    @SerialName("book_slug") val bookSlug: String,
    val volume: String,
    val status: String
)