package com.pmvb.scpiback.data.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.Persistable

@Entity
interface IColor : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    var code: String
}