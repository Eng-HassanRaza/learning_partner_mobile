package com.learningpartner.shared.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

object FlashcardSerializer : KSerializer<Flashcard> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Flashcard")

    override fun serialize(encoder: Encoder, value: Flashcard) {
        throw UnsupportedOperationException("Serialization not supported for FlashcardSerializer")
    }

    override fun deserialize(decoder: Decoder): Flashcard {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("FlashcardSerializer only works with JSON")
        val obj = input.decodeJsonElement() as? JsonObject
            ?: throw SerializationException("Expected JsonObject for Flashcard")

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

        val id = obj.getInt("id", "pk") ?: 0
        val front = obj.getString("front", "fornt", "front_text", "term", "question") ?: ""
        val back = obj.getString("back", "back_text", "definition", "answer") ?: ""
        val category = obj.getString("category", "topic")
        val difficulty = obj.getString("difficulty", "level")

        return Flashcard(
            id = id,
            front = front,
            back = back,
            category = category,
            difficulty = difficulty
        )
    }
} 