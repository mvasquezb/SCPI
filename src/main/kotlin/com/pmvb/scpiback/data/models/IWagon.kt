package com.pmvb.scpiback.data.models

import io.requery.*
import java.util.*

@Entity
interface IWagon : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:Column(nullable = true)
    var castingDate: Date?

    @get:ManyToOne
    var productionOven: IOven

    @get:ManyToOne
    var coatOperator: IOperator

    @get:ManyToOne
    var castOperator: IOperator

    @get:ManyToOne
    var polishOperator: IOperator

    @get:ManyToOne
    var position: IWagonPosition
}