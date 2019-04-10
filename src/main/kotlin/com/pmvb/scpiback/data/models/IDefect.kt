package com.pmvb.scpiback.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.ManyToOne

@Entity
interface IDefect {
    @get:Key
    @get:Generated
    val id: Int

    @get:ManyToOne
    var classification: IPieceClassification

    @get:ManyToOne
    var productionArea: IProductionArea

    @get:ManyToOne
    var defectType: IDefectType

    @get:ManyToOne
    var affectedZone: IPieceZone
}