package com.pmvb.scpiback.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.ManyToOne

@Entity
interface IRepair {
    @get:Key
    @get:Generated
    val id: Int

    var done: Boolean

    @get:ManyToOne
    var classification: IPieceClassification

    @get:ManyToOne
    var repairType: IRepairType
}