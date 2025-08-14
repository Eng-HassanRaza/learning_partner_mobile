package com.learningpartner.shared.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

object ExerciseSerializer : KSerializer<Exercise> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Exercise")

    override fun serialize(encoder: Encoder, value: Exercise) {
        throw UnsupportedOperationException("Serialization not supported for ExerciseSerializer")
    }

    override fun deserialize(decoder: Decoder): Exercise {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("ExerciseSerializer only works with JSON")
        val obj = input.decodeJsonElement() as? JsonObject
            ?: throw SerializationException("Expected JsonObject for Exercise")

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
        fun JsonObject.getStringList(vararg keys: String): List<String>? {
            for (k in keys) {
                val v = this[k]
                if (v is JsonArray) return v.mapNotNull { it.jsonPrimitive.content }
            }
            return null
        }

        val id = obj.getInt("id", "pk") ?: 0
        val type = obj.getString("type", "exercise_type", "kind") ?: ""
        val question = obj.getString("question", "prompt", "text") ?: ""
        val correctAnswer = obj.getString("correct_answer", "answer", "correct") ?: ""
        val options = obj.getStringList("options", "choices")
        val explanation = obj.getString("explanation", "explain")
        val difficulty = obj.getString("difficulty", "level")

        return Exercise(
            id = id,
            type = type,
            question = question,
            correctAnswer = correctAnswer,
            options = options,
            explanation = explanation,
            difficulty = difficulty
        )
    }
} 