package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.IPieceByWagon
import com.pmvb.scpiback.data.models.Oven
import com.pmvb.scpiback.data.models.PieceByWagon
import com.pmvb.scpiback.data.models.Wagon
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.gt

object OvensService {
    fun getAllOvens(ctx: Context) {
        val ovens = dataStore.invoke {
            val res = (select(Oven::class)
                    .join(Wagon::class) on (Oven::id eq Wagon::productionOven))
                    .groupBy(Oven.ID)
            res.get()
        }
//        ovens.forEach { oven ->
//            oven.wagons = oven.wagons.filter { wagon ->
//                val productCount =
//            }
//        }
        ctx.json(ovens)
    }

    fun getAllProductsByWagon(ctx: Context) {
        val ovenId = ctx.pathParam("oven-id").toInt()

        val products = dataStore.invoke {
            val res = select (PieceByWagon::class ) where (PieceByWagon::oven eq ovenId)
            res.get()
        }

        ctx.json(products)
    }

    fun getProductsForWagon(ctx: Context) {
        val ovenId = ctx.pathParam("oven-id").toInt()
        val wagonId = ctx.pathParam("wagon-id").toInt()

        // Get products that
        val products = dataStore.invoke {
            val res = (select (PieceByWagon::class)
                    // Belong to oven
                    where (PieceByWagon::oven eq ovenId))
                    // Belong to wagon
                    .and (PieceByWagon::wagon eq wagonId)
                    // Are available for classification
                    .and (PieceByWagon::quantity gt PieceByWagon::classifiedPieces)
            res.get()
        }

        ctx.json(products)
    }
}