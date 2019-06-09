package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.IQualityLevel
import com.pmvb.scpiback.data.models.PieceClassification
import io.javalin.Context
import io.javalin.json.JavalinJackson
import io.requery.kotlin.eq

object ReportsService {
    fun getClassificationReport(ctx: Context) {
        val shiftId = ctx.queryParam("shift", "all") as String
        val ovenId = ctx.queryParam("oven", "all") as String
        val productId = ctx.queryParam("product", "all") as String

        println("$shiftId $ovenId $productId")

        val query = dataStore.invoke {
            val res = select (PieceClassification::class)
            if (productId == "all") {
                return@invoke res.get()
            } else {
                return@invoke res.where(PieceClassification::product eq productId).get()
            }
        }
        val classifications = query.toList()
        val ovenIds = mutableMapOf<Int, Int>()
        val filtered = classifications.filter {
            val wagon = it.productionWagon

            if (ovenId != "all" && wagon.productionOven.id != ovenId.toInt()) {
                return@filter false
            }

            if (shiftId != "all" && it.workshift.workShift.id != shiftId.toInt()) {
                return@filter  false
            }

            ovenIds[it.id] = wagon.productionOven.id
            return@filter true
        }
        val filteredMap = filtered
        ctx.json(mapOf("report" to filtered, "ovenIds" to ovenIds))
    }

    fun getBreaksReport(ctx: Context) {
        val shiftId = ctx.queryParam("shift", "all") as String
        val ovenId = ctx.queryParam("oven", "all") as String
        val productId = ctx.queryParam("product", "all") as String

        println("$shiftId $ovenId $productId")

        val query = dataStore.invoke {
            val res = select (PieceClassification::class)
            if (productId == "all") {
                return@invoke res.get()
            } else {
                return@invoke res.where(PieceClassification::product eq productId).get()
            }
        }
        val classifications = query.toList()
        val ovenIds = mutableMapOf<Int, Int>()
        val filtered = classifications.filter {
            val wagon = it.productionWagon
            val assignedQ: IQualityLevel? = it.assignedQualityLevel
            val systemQ: IQualityLevel? = it.systemQualityLevel

            if (ovenId != "all" && wagon.productionOven.id != ovenId.toInt()) {
                return@filter false
            }

            if (shiftId != "all" && it.workshift.workShift.id != shiftId.toInt()) {
                return@filter  false
            }
            println("Not null")
            if (assignedQ?.code != "R" && systemQ?.code != "R") {
                return@filter false
            }

            ovenIds[it.id] = wagon.productionOven.id
            return@filter true
        }
        val filteredMap = filtered
        ctx.json(mapOf("report" to filtered, "ovenIds" to ovenIds))
    }
}