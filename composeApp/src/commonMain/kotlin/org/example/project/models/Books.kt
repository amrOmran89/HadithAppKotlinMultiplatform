package org.example.project.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Books(
    @SerialName("books") var books: List<Book>,
    @SerialName("message") val message: String,
    @SerialName("status") val status: Int
)