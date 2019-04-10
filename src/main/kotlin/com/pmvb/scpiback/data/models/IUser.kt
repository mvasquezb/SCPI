package com.pmvb.scpiback.data.models

import io.requery.*

@Entity
interface IUser : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String
    var name: String

    @get:ManyToOne
    var factory: IFactory

    @get:ManyToOne
    var role: IRole

    @get:Column(nullable = true)
    var password: String

    @get:OneToMany
    var coatedWagons: List<IWagon>

    @get:OneToMany
    var polishedWagons: List<IWagon>

    @get:OneToMany
    var castedWagons: List<IWagon>
}

/**
 * Encrypts password before saving
 */
fun IUser.setSafePassword(password: String) {
    this.password = password
}