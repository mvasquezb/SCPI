package com.pmvb.scpiback

import java.io.FileInputStream
import java.io.PrintStream
import java.util.*

object Config {
    val AppConfig: Properties by lazy {
        val fileStream = this::class.java.classLoader.getResourceAsStream("app.properties")

        val AppConfig = Properties().apply {
            load(fileStream)
            if (this["WEB_PORT"] == null) {
                this["WEB_PORT"] = System.getenv("WEB_PORT") ?: 7000
            }
            if (this["DB_HOST"] == null) {
                this["DB_HOST"] = System.getenv("DB_HOST")
            }
            if (this["DB_PORT"] == null) {
                this["DB_PORT"] = System.getenv("DB_PORT")
            }
            if (this["DB_DATABASE"] == null) {
                this["DB_DATABASE"] = System.getenv("DB_DATABASE")
            }
            if (this["DB_USERNAME"] == null) {
                this["DB_USERNAME"] = System.getenv("DB_USERNAME")
            }
            if (this["DB_PASSWORD"] == null) {
                this["DB_PASSWORD"] = System.getenv("DB_PASSWORD")
            }
            if (this["CLEARDB_DATABASE_URL"] == null) {
                this["CLEARDB_DATABASE_URL"] = System.getenv("CLEARDB_DATABASE_URL") ?: ""
            }
        }
        AppConfig
    }
}

fun main(args: Array<String>) {
    Config.AppConfig.list(PrintStream(System.out))
}