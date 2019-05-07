package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.Oven
import com.pmvb.scpiback.data.models.Wagon
import io.javalin.Context
import io.requery.kotlin.eq

object OvensService {
    fun getAllOvens(ctx: Context) {
        val ovens = dataStore.invoke {
            val res = select(Oven::class)
                    .join(Wagon::class) on (Oven::id eq Wagon::productionOven)
            res.get().toMap(Oven.ID)
        }
        ctx.json(ovens)
    }
}