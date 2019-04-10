package com.pmvb.scpiback

import com.pmvb.scpiback.data.dataStore
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.put

data class Todo(val id: Long = -1, val title: String = "", val completed: Boolean = false)

lateinit var todos: Array<Todo>

fun main(args: Array<String>) {
    todos = arrayOf(Todo(123123123, "My very first todo", false))
    val app = Javalin.create().apply {
        enableCorsForAllOrigins()
        port(AppConfig["WEB_PORT"].toString().toInt())
    }.start()

    routeSetup(app)
}

fun routeSetup(app: Javalin) {
    app.routes {
        get("/todos") { ctx ->
            dataStore
            ctx.json(todos)
        }
        put("/todos") { ctx ->
            todos = ctx.bodyAsClass(Array<Todo>::class.java)
            ctx.status(204)
        }
    }
}
