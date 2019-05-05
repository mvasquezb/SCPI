package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.ProductFamily
import com.pmvb.scpiback.data.models.ProductModel
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.invoke

object ProductsService {
    fun getAllProductFamilies(ctx: Context) {
        var families = dataStore.select(ProductFamily::class)().toList()

        val withoutEmpty = ctx.queryParamMap().containsKey("not-empty")
        if (withoutEmpty) {
            families = families.filter { fam ->
                val modelCount = dataStore.invoke {
                    val res = count(ProductModel::class) where (ProductModel::productFamily eq fam.id)
                    res().value()
                }
                modelCount > 0
            }
        }
        ctx.json(families)
    }

    fun getAllProductModels(ctx: Context) {
        ctx.json(dataStore.select(ProductModel::class)())
    }

    fun getModelsInFamily(ctx: Context) {
        val famId = ctx.pathParam("family-id").toInt()
        val fam = dataStore.invoke {
            val res = select (ProductFamily::class) where (ProductFamily::id eq famId)
            res().firstOrNull()
        }
        if (fam == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "Familia de producto no encontrada"))
            return
        }
        var models = dataStore.invoke {
            val res = select(ProductModel::class) where (ProductModel::productFamily eq fam.id)
            res().toList()
        }

        ctx.json(models)
    }

    fun getProductFamily(ctx: Context) {
        val famId = ctx.pathParam("family-id").toInt()
        val fam = dataStore.invoke {
            val res = select (ProductFamily::class) where (ProductFamily::id eq famId)
            res().firstOrNull()
        }
        if (fam == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "Familia de producto no encontrada"))
            return
        }

        ctx.json(fam)
    }
}