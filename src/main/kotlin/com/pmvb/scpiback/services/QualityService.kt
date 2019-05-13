package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.*
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.invoke

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
}