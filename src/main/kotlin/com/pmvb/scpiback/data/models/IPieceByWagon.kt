package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.requery.*

@Entity
@JsonIgnoreProperties(value = ["oven"])
interface IPieceByWagon : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:ManyToOne
    var oven: IOven

    @get:ManyToOne
    var wagon: IWagon

    @get:ManyToOne
    var productFamily: IProductFamily

    @get:ManyToOne
    var productModel: IProductModel

    @get:Column(value = "0")
    var quantity: Int

    @get:Column(value = "0")
    var classifiedPieces: Int

    @get:ManyToOne
    var color: IColor
}