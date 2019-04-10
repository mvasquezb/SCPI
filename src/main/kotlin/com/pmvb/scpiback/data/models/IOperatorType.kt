package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IRole : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    var description: String
    @get:Column(nullable = true)
    var prefix: String?
}