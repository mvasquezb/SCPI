package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IRepair : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var done: Boolean

    @get:ManyToOne
    var classification: IPieceClassification

    @get:ManyToOne
    var repairType: IRepairType
}