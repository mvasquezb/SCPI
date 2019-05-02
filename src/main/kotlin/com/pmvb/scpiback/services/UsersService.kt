package com.pmvb.scpiback.services

import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.IRole
import com.pmvb.scpiback.data.models.Role
import com.pmvb.scpiback.data.models.User
import com.pmvb.scpiback.data.models.setSafePassword
import io.javalin.Context
import io.requery.kotlin.eq
import io.requery.kotlin.invoke
import org.springframework.security.crypto.bcrypt.BCrypt

object UsersService {
    fun login(ctx: Context) {
        val params = ctx.bodyAsClass(Map::class.java).toMap()
        val username = params["username"] as String
        val password = params["password"] as String

        var matchedUser = dataStore.invoke {
            val result = select(User::class) where (User::code eq username)
            result().firstOrNull()
        }

        // Check username
        if (matchedUser == null) {
            ctx.json(mapOf("error" to "Usuario inválido"))
            ctx.status(404)
            return
        }

        // Check password
        if (!BCrypt.checkpw(password, matchedUser.password)) {
            ctx.json(mapOf("error" to "Contraseña incorrecta"))
            ctx.status(403)
            return
        }

        matchedUser.token = "${matchedUser.code}-${matchedUser.password}"
        matchedUser = dataStore.update(matchedUser)
        ctx.json(mapOf("user" to matchedUser))
        ctx.status(200)
    }

    fun getAll(ctx: Context) {
        ctx.json(dataStore.select(User::class)())
    }

    fun getRoles(ctx: Context) {
        ctx.json(dataStore.select(Role::class)())
    }
}