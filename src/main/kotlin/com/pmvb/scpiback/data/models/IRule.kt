package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.requery.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
interface IRule : Persistable {
    @get:Key
    @get:Generated
    var id: Int

    var name: String

    @get:Column(nullable = true)
    var description: String?

    var antecedent: String

    var consequentName: String
    var consequentValue: String
}