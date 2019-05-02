package com.pmvb.scpiback

import com.pmvb.scpiback.data.Connection
import com.pmvb.scpiback.router.routeSetup
import io.javalin.Javalin

data class Todo(val id: Long = -1, val title: String = "", val completed: Boolean = false)

lateinit var todos: Array<Todo>

fun main(args: Array<String>) {
    todos = arrayOf(Todo(123123123, "My very first todo", false))
    val app = Javalin.create().apply {
        enableCorsForAllOrigins()
        port(AppConfig["WEB_PORT"].toString().toInt())
    }.start()

    routeSetup(app)
    Connection.setup()
}
