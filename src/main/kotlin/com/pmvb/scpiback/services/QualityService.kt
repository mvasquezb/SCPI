package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.*
import com.pmvb.scpiback.data.models.Rule as SRule
import com.pmvb.scpies.inference.InferenceResult
import com.pmvb.scpies.inference.Proof
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.invoke
import org.jeasy.rules.api.Facts
import org.jeasy.rules.api.Rules
import org.jeasy.rules.core.InferenceEngine
import org.jeasy.rules.mvel.MVELRule
import java.lang.Exception

object QualityService {
    fun getAllQualityLevels(ctx: Context) {
        ctx.json(dataStore.select(QualityLevel::class)())
    }

    fun getAllDefectAreas(ctx: Context) {
        var areas = dataStore.select(DefectArea::class)().toList()
        val withoutEmpty = ctx.queryParamMap().containsKey("not-empty")
        if (withoutEmpty) {
            areas = areas.filter { area ->
                val defectCount = dataStore.invoke {
                    val res = count(DefectType::class) where (DefectType::defectArea eq area.id)
                    res().value()
                }
                defectCount > 0
            }
        }
        ctx.json(areas)
    }

    fun getAllDefectTypes(ctx: Context) {
        ctx.json(dataStore.select(DefectType::class)())
    }

    fun getDefectTypesForArea(ctx: Context) {
        val areaId = ctx.pathParam("area-id").toInt()
        val area = dataStore.invoke {
            val res = select (DefectArea::class) where (DefectArea::id eq areaId)
            res().firstOrNull()
        }

        if (area == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "No se encontró el área de defecto"))
            return
        }

        val defectTypes = dataStore.invoke {
            val res = select (DefectType::class) where (DefectType::defectArea eq area.id)
            res()
        }
        ctx.json(defectTypes)
    }

    fun getAllPieceZones(ctx: Context) {
        ctx.json(dataStore.select(PieceZone::class)())
    }

    fun getAllRepairTypes(ctx: Context) {
        ctx.json(dataStore.select(RepairType::class)())
    }

    fun getAllEvaluationTypes(ctx: Context) {
        ctx.json(dataStore.select(EvaluationType::class)())
    }

    fun qualityCheck(ctx: Context) {
        val defects = ctx.body<List<Defect>>()

        val facts = convertDefectsToFacts(defects)
        facts.put("facts", facts.asMap())
        val kb = getSavedRules()

        val algo = InferenceEngine()
        algo.fire(kb, facts)
        val result = InferenceResult(
                entails = facts.get<String>("CALIDAD") != null,
                proof = Proof(listOf(), facts.asMap())
        )

        val qName: String? = facts.get<String>("CALIDAD")
        val quality = dataStore.invoke {
            val res = select (QualityLevel::class) where (QualityLevel::name eq (qName?.toUpperCase() ?: ""))
            res().firstOrNull()
        }
        val res = mapOf<String, Any>(
                "qualityLevel" to quality,
                "facts" to facts.apply { remove("facts") }.asMap()
        )
        ctx.json(res)
    }

    private fun getSavedRules(): Rules {
        val dbRules = dataStore.select(SRule::class)().toList()
        val rules = dbRules.map {
            val value = parseValue(it.consequentValue)
            MVELRule()
                    .name(it.name)
                    .description(it.description)
                    .`when`(it.antecedent)
                    .then("facts.put(\"${it.consequentName}\", $value)")
        }

        return Rules(*rules.toTypedArray())
    }

    private fun parseValue(value: String): Any {
        if (value == "true" || value == "false") {
            return value.toBoolean()
        }

        return try {
            value.toInt()
        } catch (ex: Exception) {
            try {
                value.toFloat()
            } catch (ex: Exception) {
                "\"$value\""
            }
        }
    }

    private fun convertDefectsToFacts(defects: List<Defect>): Facts {
        val facts = Facts()

        facts.put("NUM_DEFECTOS", defects.size)

        defects.forEach { defect ->
            val map = convertToFacts(defect)
            map.asIterable().forEach {
                facts.put(it.key, it.value)
            }
        }

        return facts
    }

    private fun convertToFacts(defect: Defect): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        val factName = defect.defectType.factName
        val factNameParts = factName.split('_')
        if (!map.containsKey(factNameParts[0])) {
            map["NUM_${factNameParts[0]}"] = 1
        } else {
            map["NUM_${factNameParts[0]}"] = (map["NUM_${factNameParts[0]}"] as Int) + 1
        }
        map[factNameParts[0]] = true
        map["AREA_${factNameParts[0]}"] = defect.defectType.defectArea.name
        map["ZONA_${factNameParts[0]}"] = defect.affectedZone.code
        val visible = when (defect.affectedZone.visible) {
            true -> "VISIBLE"
            false -> "NO_VISIBLE"
        }
        map["${factNameParts[0]}_$visible"] = true
        if (factNameParts.size > 1) {
            val key = "${factNameParts[0]}_${factNameParts[1]}"
            map[key] = true
            map["${key}_$visible"] = true
            map["AREA_$key"] = defect.defectType.defectArea.name
            map["ZONA_$key"] = defect.affectedZone.name
            if (factNameParts.size > 2) {
                map[factName] = true
                map["${factName}_$visible"] = true
                map["AREA_$factName"] = defect.defectType.defectArea.name
                map["ZONA_$factName"] = defect.affectedZone.name
            }
        }

        return map
    }

    fun getPieceZonesByProduct(ctx: Context) {
        val productFamilyId = ctx.pathParam("product-family-id").toInt()
        val productModelId = ctx.pathParam("product-model-id").toInt()

        val zones = dataStore.invoke {
            val res = select (ZoneByPiece::class)
                    .where(ZoneByPiece::productFamily eq productFamilyId)
                    .and(
                            (ZoneByPiece::productModel eq productModelId)
                                    .or(ZoneByPiece.PRODUCT_MODEL_ID.isNull)
                    )
            res()
        }
        ctx.json(zones)
    }

    fun saveClassification(ctx: Context) {
        val classification = ctx.bodyAsClass(PieceClassification::class.java)

        val saved = dataStore.upsert(classification)
        ctx.json(saved)
    }

    fun getAllClassifications(ctx: Context) {
        ctx.json(dataStore.select(PieceClassification::class)())
    }

    fun getAllRules(ctx: Context) {
        ctx.json(dataStore.select(SRule::class)())
    }

    fun addRule(ctx: Context) {
        var rule = ctx.body<SRule>()
        println(rule)
        rule = dataStore.insert(rule)
        ctx.json(rule)
    }

    fun updateRule(ctx: Context) {
        val ruleId = ctx.pathParam("rule-id").toInt()
        var rule = dataStore.select(SRule::class).where(SRule::id eq ruleId)().firstOrNull()
        if (rule == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "No se encontró el registro"))
            return
        }
        rule = dataStore.update(rule)
        ctx.json(rule)
    }

    fun getRuleById(ctx: Context) {
        val ruleId = ctx.pathParam("rule-id").toInt()
        val rule = dataStore.select(SRule::class).where(SRule::id eq ruleId)().firstOrNull()
        if (rule == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "No se encontró el registro"))
            return
        }
        ctx.json(rule)
    }

    fun deleteRule(ctx: Context) {
        val ruleId = ctx.pathParam("rule-id").toInt()
        val rule = dataStore.select(SRule::class).where(SRule::id eq ruleId)().firstOrNull()
        if (rule == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "No se encontró el registro"))
            return
        }
//        dataStore.delete(SRule::class).where(SRule::id eq ruleId)()
        ctx.json(rule)
    }
}