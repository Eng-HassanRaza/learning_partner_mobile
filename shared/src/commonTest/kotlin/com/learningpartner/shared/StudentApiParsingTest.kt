package com.learningpartner.shared

import com.learningpartner.shared.data.api.StudentApi
import com.learningpartner.shared.domain.models.*
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
import kotlin.test.assertTrue

class StudentApiParsingTest {
    private fun buildClient(handler: suspend (HttpRequestData) -> HttpResponseData): HttpClient {
        return HttpClient(MockEngine { request -> handler(request) }) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    @Test
    fun parsesClassesPaginated() = runTest {
        val json = """
        {"count":1,"next":null,"previous":null,"results":[
          {"id":1,"name":"A2 Class","teacher":{"id":5,"user":{"id":9,"username":"s","email":"s@example.com","first_name":"Stu","last_name":"Dent"}},
           "level":{"id":2,"level_code":"A2","level_name":"Elementary"},
           "class_type":"group","max_students":10,"current_students":5,
           "description":null,"start_date":null,"end_date":null}
        ]}
        """.trimIndent()
        val client = buildClient { _ ->
            respond(json, headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val api = StudentApi(client)
        val classes = api.getClasses()
        assertEquals(1, classes.size)
        assertEquals("A2 Class", classes[0].name)
        assertEquals("A2", classes[0].level.levelCode)
    }

    @Test
    fun parsesSessionsWithNestedContentAndVocabulary() = runTest {
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
             "vocabulary":[{"id":1,"word":"hola","translation":"hello","part_of_speech":"interjection"}],
             "grammar_tables":[{"id":1,"title":"Present","description":"","rules":[{"id":1,"rule":"r","explanation":"e"}],"examples":["ex"]}],
             "practice_exercises":[{"id":1,"type":"translation","question":"q","correct_answer":"a"}],
             "flashcards":[{"id":1,"front":"f","back":"b"}],
             "speaking_practice":["say hi"]
           }
          }
        ]}
        """.trimIndent()
        val client = buildClient { _ ->
            respond(json, headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val api = StudentApi(client)
        val sessions = api.getSessions()
        assertEquals(1, sessions.size)
        val content = sessions[0].content
        assertTrue(content != null)
        assertEquals(1, content!!.vocabulary.size)
        assertEquals("part of speech mapping", "interjection", content.vocabulary[0].partOfSpeech)
    }
} 