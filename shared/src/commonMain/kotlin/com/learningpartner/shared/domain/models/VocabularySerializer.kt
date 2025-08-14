package com.learningpartner.shared.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

object VocabularySerializer : KSerializer<Vocabulary> {
	override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Vocabulary")

	override fun serialize(encoder: Encoder, value: Vocabulary) {
		// Default serialization is fine for client usage
		throw UnsupportedOperationException("Serialization not supported for VocabularySerializer")
	}

	override fun deserialize(decoder: Decoder): Vocabulary {
		val input = decoder as? JsonDecoder
			?: throw SerializationException("VocabularySerializer only works with JSON")
		val obj = input.decodeJsonElement() as? JsonObject
			?: throw SerializationException("Expected JsonObject for Vocabulary")

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
		val word = obj.getString("word", "term") ?: ""
		val translation = obj.getString("translation", "meaning", "definition") ?: ""
		val partOfSpeech = obj.getString("part_of_speech", "partOfSpeech", "pos") ?: ""
		val example = obj.getString("example", "sentence")
		val difficulty = obj.getString("difficulty", "level")

		return Vocabulary(
			id = id,
			word = word,
			translation = translation,
			partOfSpeech = partOfSpeech,
			example = example,
			difficulty = difficulty
		)
	}
} 