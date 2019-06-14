package com.pmvb.scpiback

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.pmvb.scpiback.data.Connection
import com.pmvb.scpiback.router.routeSetup
import io.javalin.Javalin
import io.javalin.core.util.OptionalDependency
import io.javalin.json.JavalinJackson


fun main(args: Array<String>) {
    configureJsonMapper()

    val app = Javalin.create().apply {
        enableCorsForAllOrigins()
    }.start(getAssignedPort())

    routeSetup(app)
    Connection.setup()
}

fun getAssignedPort(): Int {
    val herokuPort = System.getenv("PORT")
    return herokuPort?.toInt() ?: Config.AppConfig["WEB_PORT"].toString().toInt()
}

fun configureJsonMapper() {
    val className = OptionalDependency.JACKSON_KT.testClass
    val mapper = ObjectMapper().registerModule(Class.forName(className).getConstructor().newInstance() as Module)
    mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, "className")
    JavalinJackson.configure(mapper)
}
