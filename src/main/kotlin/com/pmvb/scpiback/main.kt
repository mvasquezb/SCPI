package com.pmvb.scpiback

import com.fasterxml.jackson.databind.ObjectMapper
import com.pmvb.scpiback.data.Connection
import com.pmvb.scpiback.router.routeSetup
import io.javalin.Javalin
import io.javalin.json.JavalinJackson
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.deser.Deserializers
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder
import com.fasterxml.jackson.databind.type.SimpleType
import io.javalin.core.util.OptionalDependency


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
