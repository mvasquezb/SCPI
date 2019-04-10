package com.pmvb.scpiback.models

import io.requery.*

@Entity
interface IPieceClassification {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    var quantity: Int

    @get:ManyToOne
    var productionWagon: IWagon

    @get:ManyToOne
    var product: IProduct

    @get:ManyToOne
    var qualityLevel: IQualityLevel

    @get:ManyToOne
    var pieceType: IPieceType

    @get:ManyToOne
    var classifierOperator: IOperator
}