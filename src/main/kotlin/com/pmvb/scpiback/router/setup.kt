package com.pmvb.scpiback.router

import com.pmvb.scpiback.services.*
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun routeSetup(app: Javalin) {
    app.routes {
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
        path("/piece-zones") {
            get(QualityService::getAllPieceZones)
            get(":product-family-id/:product-model-id", QualityService::getPieceZonesByProduct)
        }
        get("/repair-types", QualityService::getAllRepairTypes)
        get("/evaluation-types", QualityService::getAllEvaluationTypes)
        post("/quality-check", QualityService::qualityCheck)
        post("/classification", QualityService::saveClassification)
        get("/classifications", QualityService::getAllClassifications)
        path("rules") {
            get(QualityService::getAllRules)
            post(QualityService::addRule)
            path(":rule-id") {
                get(QualityService::getRuleById)
                post(QualityService::updateRule)
                delete(QualityService::deleteRule)
            }
        }
    }
}