package com.pmvb.scpiback

import java.io.FileInputStream
import java.io.PrintStream
import java.util.*

val rootPath = Thread.currentThread().contextClassLoader.getResource("").path

val configFile = "${rootPath}app.properties"

val AppConfig = Properties().apply {
    load(FileInputStream(configFile))
    if (this["WEB_PORT"] == null) {
        this["WEB_PORT"] = System.getenv("WEB_PORT") ?: 7000
    }
    if (this["DB_HOST"] == null) {
        this["DB_HOST"] = System.getenv("DB_HOST") ?: "localhost"
    }
    if (this["DB_PORT"] == null) {
        this["DB_PORT"] = System.getenv("DB_PORT") ?: 3306
    }
    if (this["DB_DATABASE"] == null) {
        this["DB_DATABASE"] = System.getenv("DB_DATABASE") ?: "SCPIV2"
    }
    if (this["DB_USERNAME"] == null) {
        this["DB_USERNAME"] = System.getenv("DB_USERNAME") ?: "tesis"
    }
    if (this["DB_PASSWORD"] == null) {
        this["DB_PASSWORD"] = System.getenv("DB_PASSWORD") ?: "tesis2"
    }
}

fun main(args: Array<String>) {
    AppConfig.list(PrintStream(System.out))
}