package com.pmvb.scpiback

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.staticfiles.Location

data class Todo(val id: Long = -1, val title: String = "", val completed: Boolean = false)

fun main(args: Array<String>) {
    var todos = arrayOf(Todo(123123123, "My very first todo", false))

    val app = Javalin.create().apply {
        port(7000)
        enableStaticFiles("src/web", Location.EXTERNAL)
    }.start()

    app.routes {
        get("/todos") { ctx ->
            ctx.json(todos)
        }
        put("/todos") { ctx ->
            todos = ctx.bodyAsClass(Array<Todo>::class.java)
            ctx.status(204)
        }
    }
}