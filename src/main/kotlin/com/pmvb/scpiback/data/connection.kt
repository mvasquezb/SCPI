package com.pmvb.scpiback.data

import com.microsoft.sqlserver.jdbc.SQLServerDataSource
import com.mysql.cj.jdbc.MysqlDataSource
import com.pmvb.scpiback.AppConfig
import com.pmvb.scpiback.data.models.Defect
import com.pmvb.scpiback.data.models.DefectArea
import com.pmvb.scpiback.data.models.Models
import com.pmvb.scpiback.data.models.User
import io.requery.Persistable
import io.requery.kotlin.invoke
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.SchemaModifier
import io.requery.sql.TableCreationMode
import javax.sql.CommonDataSource

val connectionUrl = "jdbc:${AppConfig["DB_DRIVER"]}://${AppConfig["DB_HOST"]}:${AppConfig["DB_PORT"]}"

val dataSource: CommonDataSource = when(AppConfig["DB_DRIVER"].toString().toLowerCase()) {
    "mysql" -> MysqlDataSource().apply {
        setURL("$connectionUrl/${AppConfig["DB_DATABASE"]}?serverTimezone=UTC")
        user = AppConfig["DB_USERNAME"].toString()
        password = AppConfig["DB_PASSWORD"].toString()
    }
    "sqlserver" -> SQLServerDataSource().apply {
        url = "$connectionUrl;database=${AppConfig["DB_DATABASE"]}"
        user = AppConfig["DB_USERNAME"].toString()
        setPassword(AppConfig["DB_PASSWORD"].toString())
    }
    else -> throw IllegalArgumentException("Database driver not supported")
}


val configuration = KotlinConfiguration(
        dataSource = dataSource,
        model = Models.DEFAULT,
        useDefaultLogging = true
).apply {
    SchemaModifier(this).createTables(TableCreationMode.CREATE_NOT_EXISTS)
}

val dataStore = KotlinEntityDataStore<Persistable>(configuration)

fun main(args: Array<String>) {
    println(connectionUrl)
    println(AppConfig["DB_USERNAME"])
    println(AppConfig["DB_PASSWORD"])
    println(dataSource::class.java)
    val result = dataStore.select(Defect::class)()
    println(result)
}