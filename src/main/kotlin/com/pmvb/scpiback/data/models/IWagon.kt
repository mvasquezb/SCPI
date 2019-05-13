package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.requery.*

@Entity
@JsonIgnoreProperties("productionOven")
interface IWagon : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:ManyToOne
    var productionOven: IOven

    @get:ManyToOne
    var coatOperator: IUser

    @get:ManyToOne
    var polishOperator: IUser

    @get:ManyToOne
    var castOperator: IUser
}