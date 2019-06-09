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
        port(AppConfig["WEB_PORT"].toString().toInt())
    }.start()

    routeSetup(app)
    Connection.setup()
}

fun configureJsonMapper() {
    val className = OptionalDependency.JACKSON_KT.testClass
    val mapper = ObjectMapper().registerModule(Class.forName(className).getConstructor().newInstance() as Module)
    mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, "className")
    JavalinJackson.configure(mapper)
}
