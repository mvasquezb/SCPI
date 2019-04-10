package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IOperatorWorkShift : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:ManyToOne
    var operator: IUser

    @get:ManyToOne
    var workShift: IWorkShift
}