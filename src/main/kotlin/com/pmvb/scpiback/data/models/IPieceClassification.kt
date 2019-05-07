package com.pmvb.scpiback.data.models

import io.requery.*
import java.util.*

@Entity
interface IPieceClassification : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    var quantity: Int

    @get:ManyToOne
    var productionWagon: IWagon

    @get:ManyToOne
    var product: IProductModel

    @get:ManyToOne
    var qualityLevel: IQualityLevel

    @get:ManyToOne
    var classifierOperator: IUser

    @get:ManyToOne
    var color: IColor

    @get:ManyToOne
    var coatOperator: IUser

    @get:ManyToOne
    var polishOperator: IUser

    @get:ManyToOne
    var castOperator: IUser

    @get:Column(nullable = true)
    var castingDate: Date?

    @get:ManyToOne
    var wagonPosition: IWagonPosition
}