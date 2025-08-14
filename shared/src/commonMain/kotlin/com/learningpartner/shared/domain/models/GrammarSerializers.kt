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
import kotlinx.serialization.json.jsonPrimitive

object GrammarRuleSerializer : KSerializer<GrammarRule> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GrammarRule")

    override fun serialize(encoder: Encoder, value: GrammarRule) {
        throw UnsupportedOperationException("Serialization not supported for GrammarRuleSerializer")
    }

    override fun deserialize(decoder: Decoder): GrammarRule {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("GrammarRuleSerializer only works with JSON")
        val obj = input.decodeJsonElement() as? JsonObject
            ?: throw SerializationException("Expected JsonObject for GrammarRule")

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

        val id = obj.getInt("id") ?: 0
        val rule = obj.getString("rule", "rule_text", "text") ?: ""
        val explanation = obj.getString("explanation", "explain", "description")

        return GrammarRule(
            id = id,
            rule = rule,
            explanation = explanation
        )
    }
}

object GrammarTableSerializer : KSerializer<GrammarTable> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GrammarTable")

    override fun serialize(encoder: Encoder, value: GrammarTable) {
        throw UnsupportedOperationException("Serialization not supported for GrammarTableSerializer")
    }

    override fun deserialize(decoder: Decoder): GrammarTable {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("GrammarTableSerializer only works with JSON")
        val obj = input.decodeJsonElement()

        // Some backends may send grammar as just a title string; accept that
        if (obj is kotlinx.serialization.json.JsonPrimitive && obj.isString) {
            return GrammarTable(
                id = 0,
                title = obj.content,
                description = null,
                rules = emptyList(),
                examples = emptyList()
            )
        }

        val json = obj as? JsonObject
            ?: throw SerializationException("Expected JsonObject for GrammarTable")

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

        val id = json.getInt("id") ?: 0
        val title = json.getString("title", "name") ?: ""
        val description = json.getString("description", "details")

        val rulesArray = when (val r = json["rules"]) {
            is JsonArray -> r
            else -> JsonArray(emptyList())
        }
        val rules = rulesArray.mapNotNull { element ->
            val ruleObj = element as? JsonObject ?: return@mapNotNull null
            GrammarRule(
                id = ruleObj["id"]?.jsonPrimitive?.content?.toIntOrNull() ?: 0,
                rule = (ruleObj["rule"] ?: ruleObj["rule_text"] ?: ruleObj["text"])?.jsonPrimitive?.content ?: "",
                explanation = (ruleObj["explanation"] ?: ruleObj["explain"] ?: ruleObj["description"])?.jsonPrimitive?.content
            )
        }

        val examplesArray = when (val e = json["examples"]) {
            is JsonArray -> e
            else -> JsonArray(emptyList())
        }
        val examples = examplesArray.mapNotNull { element ->
            val prim = element.jsonPrimitive
            if (prim.isString) prim.content else null
        }

        return GrammarTable(
            id = id,
            title = title,
            description = description,
            rules = rules,
            examples = examples
        )
    }
} 