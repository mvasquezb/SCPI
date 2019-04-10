package com.pmvb.scpiback.models

import io.requery.*

@Entity
interface IDefectType {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String
    var name: String

    @get:ManyToOne
    var productionArea: IProductionArea
}