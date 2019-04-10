package com.pmvb.scpiback

import java.io.FileInputStream
import java.io.PrintStream
import java.util.*

val rootPath = Thread.currentThread().contextClassLoader.getResource("").path

val configFile = "${rootPath}app.properties"

val AppConfig = Properties().apply { load(FileInputStream(configFile)) }

fun main(args: Array<String>) {
    AppConfig.list(PrintStream(System.out))
}