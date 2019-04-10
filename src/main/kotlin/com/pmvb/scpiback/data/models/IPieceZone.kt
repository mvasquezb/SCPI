package com.pmvb.scpiback.data.models

import com.pmvb.scpiback.data.models.IPieceType
import io.requery.*

@Entity
interface IPieceZone : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String

    var name: String

    @get:ManyToOne
    var pieceType: IPieceType
}