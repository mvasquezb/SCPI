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
}

/**
 * Encrypts password before saving
 */
fun IUser.setSafePassword(password: String) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt())
}