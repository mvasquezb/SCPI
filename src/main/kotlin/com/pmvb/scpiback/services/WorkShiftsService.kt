package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection
import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.IWorkShift
import com.pmvb.scpiback.data.models.OperatorWorkShift
import com.pmvb.scpiback.data.models.User
import com.pmvb.scpiback.data.models.WorkShift
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.invoke

object WorkShiftsService {
    fun createUserWorkShift(ctx: Context) {
        val userId = ctx.pathParam("user-id").toInt()
        val user = Connection.dataStore.invoke {
            val res = select (User::class) where (User::id eq userId)
            res().firstOrNull()
        }
        if (user == null) {
            ctx.json(mapOf("error" to "Usuario no encontrado"))
            ctx.status(404)
            return
        }
        val shiftType = dataStore.invoke {
            val res = select (WorkShift::class) where (WorkShift::code eq "T03")
            res().first()
        }
        val params = ctx.bodyAsClass(Map::class.java).toMap()
        val shiftCode = params["shiftCode"] as String
        var shift = OperatorWorkShift().apply {
            code = shiftCode
            operator = user
            workShift = shiftType
        }
        shift = dataStore.insert(shift)
        ctx.json(shift)
    }

    fun getShiftTypes(ctx: Context) {
        ctx.json(dataStore.select(IWorkShift::class)())
    }
}