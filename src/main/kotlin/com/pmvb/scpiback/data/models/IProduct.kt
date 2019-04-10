package com.pmvb.scpiback.models

import com.pmvb.scpiback.data.models.IColor
import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.ManyToOne

@Entity
interface IProduct {
    @get:Key
    @get:Generated
    val id: Int

    var name: String
    @get:ManyToOne
    var productModel: IProductModel
    @get:ManyToOne
    var color: IColor
}