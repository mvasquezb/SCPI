package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.requery.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
interface IOperatorWorkShift : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var operator: IUser

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var workShift: IWorkShift
}