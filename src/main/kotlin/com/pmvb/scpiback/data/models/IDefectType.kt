package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IDefectType : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String
    var name: String

    @get:ManyToOne
    var defectArea: IDefectArea
}