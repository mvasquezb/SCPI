package com.pmvb.scpiback.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key

@Entity
interface IProductionArea {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    var code: String
}