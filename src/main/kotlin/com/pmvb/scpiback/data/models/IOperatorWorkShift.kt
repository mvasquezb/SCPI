package com.pmvb.scpiback.data.models

import com.pmvb.scpiback.data.models.IOperator
import com.pmvb.scpiback.data.models.IWorkShift
import io.requery.*

@Entity
interface IOperatorWorkShift : Persistable {
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