package com.pmvb.scpiback.data.models

import io.requery.*
import org.springframework.security.crypto.bcrypt.BCrypt

@Entity
interface IUser : Persistable {
    @get:Key
    @get:Generated
    val id: Int

    @get:Column(unique = true)
    var code: String
    var name: String
    var token: String

    @get:ManyToOne
    var factory: IFactory

    @get:ManyToOne
    var role: IRole

    @get:Column(nullable = true)
    var password: String

    @get:OneToMany(mappedBy = "coatOperator")
    var coatedWagons: List<IWagon>

    @get:OneToMany(mappedBy = "polishOperator")
    var polishedWagons: List<IWagon>

    @get:OneToMany(mappedBy = "castOperator")
    var castedWagons: List<IWagon>
}

/**
 * Encrypts password before saving
 */
fun IUser.setSafePassword(password: String) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt())
}