package com.pmvb.scpiback.router

import com.pmvb.scpiback.Todo
import com.pmvb.scpiback.services.*
import com.pmvb.scpiback.todos
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.*
import org.eclipse.jetty.util.thread.strategy.ProduceConsume

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
            path(":user-id") {
                get(UsersService::getUser)
                post("create-shift", WorkShiftsService::createUserWorkShift)
            }
        }
//        path("roles") {
//            get(UsersService::getRoles)
//        }
        path("products") {
            get(ProductsService::getAllProductModels)
        }
        path("product-families") {
            get(ProductsService::getAllProductFamilies)
            path(":family-id") {
                get(ProductsService::getProductFamily)
                get("models", ProductsService::getModelsInFamily)
            }
        }
        get("/colors", ProductsService::getAllProductColors)
        get("/ovens", OvensService::getAllOvens)

        get("/quality-levels", QualityService::getAllQualityLevels)
        path("/defect-areas") {
            get(QualityService::getAllDefectAreas)
            path(":area-id") {
                get("defects", QualityService::getDefectTypesForArea)
            }
        }
        path("/defect-types") {
            get(QualityService::getAllDefectTypes)
        }
        get("/piece-zones", QualityService::getAllPieceZones)
        get("/repair-types", QualityService::getAllRepairTypes)
        get("/evaluation-types", QualityService::getAllEvaluationTypes)
    }
}