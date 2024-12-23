package org.example.project.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentLength
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.models.Books
import org.example.project.models.Chapters
import org.example.project.models.HadithResponse

object API {
    private const val API_KEY = "$2y$10$7Jqs13HzYgMA8WWoe0hnmeMQY9FLAQFlYZWjrijeUnXQB6hFZ68O"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
        }
        install(Logging) {
            level = LogLevel.ALL // Logs headers, bodies, and more
        }
    }


    suspend fun getAllBooks(): Books {
        val response = client.get("https://www.hadithapi.com/api/books") {
            contentLength()
            contentType(ContentType.Application.Json)
            header("Accept-Encoding", "identity")
            parameter("apiKey", API_KEY)
        }.body<Books>()
        return response
    }

    suspend fun getAllChapters(bookSlug: String): Chapters {
        val response = client.get("https://www.hadithapi.com/api/$bookSlug/chapters") {
            contentLength()
            contentType(ContentType.Application.Json)
            header("Accept-Encoding", "identity")
            parameter("apiKey", API_KEY)
        }.body<Chapters>()
        return response
    }

    suspend fun getAllHadith(bookSlug: String, chapterNumber: Int, page: Int = 1): HadithResponse {
        val response = client.get("https://www.hadithapi.com/api/hadiths") {
            parameter("book", bookSlug)
            parameter("chapter", chapterNumber)
            parameter("apiKey", API_KEY)
            parameter("page", page)
        }
            .body<HadithResponse>()
        return response
    }
}