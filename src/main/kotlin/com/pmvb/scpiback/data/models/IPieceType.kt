package com.pmvb.scpiback.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.ManyToOne

@Entity
interface IPieceType {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    @get:ManyToOne
    var product: IProduct
}