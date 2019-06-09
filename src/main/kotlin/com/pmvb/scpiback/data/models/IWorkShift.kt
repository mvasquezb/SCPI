package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.requery.*

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
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