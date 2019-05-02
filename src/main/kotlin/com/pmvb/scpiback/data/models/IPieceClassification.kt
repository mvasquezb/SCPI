package com.pmvb.scpiback.data.models

import io.requery.*

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
    var pieceType: IPieceType

    @get:ManyToOne
    var classifierOperator: IUser
}