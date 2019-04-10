package com.pmvb.scpiback.models

import io.requery.*

@Entity
interface IOperator {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String
    var name: String

    @get:ManyToOne
    var factory: IFactory

    @get:ManyToOne
    var operatorType: IOperatorType
}