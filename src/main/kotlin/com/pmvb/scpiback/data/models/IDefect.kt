package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IDefect : Persistable {
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