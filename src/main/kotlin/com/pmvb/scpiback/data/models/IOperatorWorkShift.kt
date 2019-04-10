package com.pmvb.scpiback.models

import io.requery.*

@Entity
interface IOperatorWorkShift {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:ManyToOne
    var operator: IOperator

    @get:ManyToOne
    var workShift: IWorkShift
}