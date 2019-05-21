package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.requery.*

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
interface IZoneByPiece {
    @get:Key
    @get:Generated
    val id: Int

    @get:ManyToOne
    var pieceZone: IPieceZone

    @get:ManyToOne
    var productFamily: IProductFamily

    @get:ManyToOne
    @get:Column(nullable = true)
    var productModel: IProductModel?
}