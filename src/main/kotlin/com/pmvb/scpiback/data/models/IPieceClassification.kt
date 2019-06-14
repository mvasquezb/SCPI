package com.pmvb.scpiback.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.requery.*
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
interface IPieceClassification : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var quantity: Int

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var productionWagon: IWagon

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var product: IProductModel

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    @get:ForeignKey(update = ReferentialAction.NO_ACTION)
    var assignedQualityLevel: IQualityLevel

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    @get:ForeignKey(update = ReferentialAction.NO_ACTION)
    var systemQualityLevel: IQualityLevel

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var classifierOperator: IUser

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var color: IColor

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var coatOperator: IUser

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var polishOperator: IUser

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var castOperator: IUser

    @get:Column(nullable = true)
    var castingDate: Date?

    @get:Column(nullable = true)
    var wagonPosition: String?

    @get:OneToMany
    var defects: List<IDefect>

    @get:ManyToOne(cascade = [CascadeAction.NONE])
    var workshift: IOperatorWorkShift
}