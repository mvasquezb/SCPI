package com.pmvb.scpiback.router

import com.pmvb.scpiback.Todo
import com.pmvb.scpiback.services.UsersService
import com.pmvb.scpiback.todos
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.*

fun routeSetup(app: Javalin) {
    app.routes {
        ApiBuilder.get("/todos") { ctx ->
            ctx.json(todos)
        }
        ApiBuilder.put("/todos") { ctx ->
            todos = ctx.bodyAsClass(Array<Todo>::class.java)
            ctx.status(204)
        }
        path("login") {
            post(UsersService::login)
        }
        path("users") {
            get(UsersService::getAll)
        }
//        path("roles") {
//            get(UsersService::getRoles)
//        }
    }
}