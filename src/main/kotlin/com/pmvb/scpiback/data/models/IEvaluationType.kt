package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IEvaluationType : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    var name: String
}
