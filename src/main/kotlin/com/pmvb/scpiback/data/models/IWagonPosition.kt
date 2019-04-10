package com.pmvb.scpiback.models

import io.requery.Entity
import io.requery.Generated
import io.requery.Key

@Entity
interface IWagonPosition {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
}