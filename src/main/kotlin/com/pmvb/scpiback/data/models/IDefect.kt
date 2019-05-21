package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.requery.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true, value = ["classification"])
interface IDefect : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var classification: IPieceClassification

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var defectType: IDefectType

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var affectedZone: IPieceZone
}