package com.pmvb.scpiback.data.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key

@Entity
interface IColor {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    var code: String
}