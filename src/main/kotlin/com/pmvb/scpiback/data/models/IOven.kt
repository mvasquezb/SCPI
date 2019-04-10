package com.pmvb.scpiback.models

import io.requery.*

@Entity
interface IOven {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:ManyToOne
    var operatorShift: IOperatorWorkShift
}