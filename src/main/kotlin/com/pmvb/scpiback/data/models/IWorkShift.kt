package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IWorkShift : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var name: String

    @get:Column(unique = true)
    var code: String

    var startTime: String
    var endTime: String
}