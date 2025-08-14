package com.learningpartner.shared.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

object SessionContentSerializer : KSerializer<SessionContent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("SessionContent")

    override fun serialize(encoder: Encoder, value: SessionContent) {
        throw UnsupportedOperationException("Serialization not supported for SessionContentSerializer")
    }

    override fun deserialize(decoder: Decoder): SessionContent {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("SessionContentSerializer only works with JSON")
        val obj = input.decodeJsonElement() as? JsonObject
            ?: throw SerializationException("Expected JsonObject for SessionContent")

        fun JsonObject.getString(vararg keys: String, default: String? = null): String? {
            for (k in keys) {
                val v = this[k]
                if (v != null) return v.jsonPrimitive.content
            }
            return default
        }
        fun JsonObject.getInt(vararg keys: String, default: Int? = null): Int? {
            for (k in keys) {
                val v = this[k]
                if (v != null) return v.jsonPrimitive.content.toIntOrNull()
            }
            return default
        }
        fun JsonObject.getArray(vararg keys: String): JsonArray? {
            for (k in keys) {
                val v = this[k]
                if (v is JsonArray) return v
            }
            return null
        }
        fun JsonArray.mapStrings(): List<String> = this.mapNotNull { el ->
            (el as? JsonPrimitive)?.takeIf { it.isString }?.content
        }

        val id = obj.getInt("id") ?: 0
        val summary = obj.getString("summary") ?: ""

        // Vocabulary
        val vocabArray = obj.getArray("vocabulary", "vocab") ?: JsonArray(emptyList())
        val vocabulary = vocabArray.mapNotNull { el ->
            val v = el as? JsonObject ?: return@mapNotNull null
            val vid = v.getInt("id", "pk") ?: 0
            val word = v.getString("word", "term") ?: ""
            val translation = v.getString("translation", "meaning", "definition") ?: ""
            val pos = v.getString("part_of_speech", "partOfSpeech", "pos") ?: ""
            val example = v.getString("example", "sentence")
            val difficulty = v.getString("difficulty", "level")
            Vocabulary(vid, word, translation, pos, example, difficulty)
        }

        // Grammar tables
        val grammarArray = obj.getArray("grammar_tables", "grammar") ?: JsonArray(emptyList())
        val grammarTables = grammarArray.mapNotNull { ge ->
            when (ge) {
                is JsonPrimitive -> GrammarTable(0, ge.content, null, emptyList(), emptyList())
                is JsonObject -> {
                    val gid = ge.getInt("id") ?: 0
                    val title = ge.getString("title", "name") ?: ""
                    val description = ge.getString("description", "details")
                    val rules = (ge["rules"] as? JsonArray)?.mapNotNull { re ->
                        val ro = re as? JsonObject ?: return@mapNotNull null
                        GrammarRule(
                            id = ro["id"]?.jsonPrimitive?.content?.toIntOrNull() ?: 0,
                            rule = (ro["rule"] ?: ro["rule_text"] ?: ro["text"])?.jsonPrimitive?.content ?: "",
                            explanation = (ro["explanation"] ?: ro["explain"] ?: ro["description"])?.jsonPrimitive?.content
                        )
                    } ?: emptyList()
                    val examples = (ge["examples"] as? JsonArray)?.mapStrings() ?: emptyList()
                    GrammarTable(gid, title, description, rules, examples)
                }
                else -> null
            }
        }

        // Practice exercises (handle misspelling)
        val exercisesArray = obj.getArray("practice_exercises", "practice_expericses") ?: JsonArray(emptyList())
        val practiceExercises = exercisesArray.mapNotNull { ee ->
            val eobj = ee as? JsonObject ?: return@mapNotNull null
            val eid = eobj.getInt("id", "pk") ?: 0
            val type = eobj.getString("type", "exercise_type", "kind") ?: ""
            val question = eobj.getString("question", "prompt", "text") ?: ""
            val correct = eobj.getString("correct_answer", "answer", "correct") ?: ""
            val options = (eobj["options"] as? JsonArray)?.mapStrings() ?: (eobj["choices"] as? JsonArray)?.mapStrings()
            val explanation = eobj.getString("explanation", "explain")
            val difficulty = eobj.getString("difficulty", "level")
            Exercise(eid, type, question, correct, options, explanation, difficulty)
        }

        // Flashcards
        val flashArray = obj.getArray("flashcards", "flash_cards") ?: JsonArray(emptyList())
        val flashcards = flashArray.mapNotNull { fe ->
            val fo = fe as? JsonObject ?: return@mapNotNull null
            val fid = fo.getInt("id", "pk") ?: 0
            val front = fo.getString("front", "fornt", "front_text", "term", "question") ?: ""
            val back = fo.getString("back", "back_text", "definition", "answer") ?: ""
            val category = fo.getString("category", "topic")
            val difficulty = fo.getString("difficulty", "level")
            Flashcard(fid, front, back, category, difficulty)
        }

        // Speaking practice
        val speakingArray = obj.getArray("speaking_practice", "speakingPractice") ?: JsonArray(emptyList())
        val speakingPractice = speakingArray.mapStrings()

        return SessionContent(
            id = id,
            summary = summary,
            vocabulary = vocabulary,
            grammarTables = grammarTables,
            practiceExercises = practiceExercises,
            flashcards = flashcards,
            speakingPractice = speakingPractice
        )
    }
} 