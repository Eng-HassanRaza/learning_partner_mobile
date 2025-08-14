package com.learningpartner.shared

import com.learningpartner.shared.data.api.StudentApi
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ExerciseParsingTest {
    private fun buildClient(handler: suspend (HttpRequestData) -> HttpResponseData): HttpClient {
        return HttpClient(MockEngine { request -> handler(request) }) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true; isLenient = true })
            }
        }
    }

    @Test
    fun parsesExercisesWithAlternateKeysAndMisspellings() = runTest {
        val json = """
        {"count":1,"next":null,"previous":null,"results":[
          {"id":10,
           "class_session":{"id":1,"name":"A2 Class","teacher":{"id":5,"user":{"id":9,"username":"s","email":"s@example.com","first_name":"Stu","last_name":"Dent"}},
             "level":{"id":2,"level_code":"A2","level_name":"Elementary"},
             "class_type":"group","max_students":10,"current_students":5,
             "description":null,"start_date":null,"end_date":null},
           "processing_status":"completed","video_duration":1200,"language_detected":"en",
           "content":{
             "id":7,"summary":"Intro",
             "vocabulary":[],
             "grammar_tables":[],
             "practice_expericses":[{"id":1,"kind":"multiple_choice","prompt":"Q?","correct":"A","choices":["A","B"]}],
             "flashcards":[],
             "speaking_practice":[]
           }
          }
        ]}
        """.trimIndent()
        val client = buildClient { _ ->
            respond(json, headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val api = StudentApi(client)
        val ex = sessions[0].content!!.practiceExercises
        assertEquals(1, ex.size)
        assertEquals("multiple_choice", ex[0].type)
        assertEquals("Q?", ex[0].question)
        assertEquals("A", ex[0].correctAnswer)
        assertEquals(listOf("A", "B"), ex[0].options)
    }
} 