package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.DefectArea
import com.pmvb.scpiback.data.models.DefectType
import com.pmvb.scpiback.data.models.PieceZone
import com.pmvb.scpiback.data.models.QualityLevel
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.invoke

object QualityService {
    fun getAllQualityLevels(ctx: Context) {
        ctx.json(dataStore.select(QualityLevel::class)())
    }

    fun getAllDefectAreas(ctx: Context) {
        ctx.json(dataStore.select(DefectArea::class)())
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
}