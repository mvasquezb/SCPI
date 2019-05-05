package com.pmvb.scpiback.data.models

import com.pmvb.scpiback.data.models.IOperatorWorkShift
import io.requery.*

@Entity
interface IOven : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    @get:OneToMany
    var producedWagons: List<IWagon>
}