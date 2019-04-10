package com.pmvb.scpiback.models

import io.requery.*

@Entity
interface IProductModel {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String
    var name: String

    @get:ManyToOne
    var productFamily: IProductFamily
}