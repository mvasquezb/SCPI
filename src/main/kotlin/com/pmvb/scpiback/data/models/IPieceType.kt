package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IPieceType : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    @get:ManyToOne
    var product: IProduct
}