package com.pmvb.scpiback.data

import com.microsoft.sqlserver.jdbc.SQLServerDataSource
import com.mysql.cj.jdbc.MysqlDataSource
import com.pmvb.scpiback.Config
import com.pmvb.scpiback.data.Connection.connectionUrl
import com.pmvb.scpiback.data.Connection.dataSource
import com.pmvb.scpiback.data.Connection.dataStore
import com.pmvb.scpiback.data.models.Defect
import com.pmvb.scpiback.data.models.Models
import io.requery.Persistable
import io.requery.kotlin.invoke
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.SchemaModifier
import io.requery.sql.TableCreationMode
import java.util.*
import javax.sql.CommonDataSource

object Connection {
    val AppConfig: Properties by lazy {
        Config.AppConfig
    }
    val connectionUrl = "jdbc:" + when (AppConfig["CLEARDB_DATABASE_URL"].toString().isEmpty()) {
        true -> "${AppConfig["DB_DRIVER"]}://${AppConfig["DB_HOST"]}:${AppConfig["DB_PORT"]}"
        false -> AppConfig["CLEARDB_DATABASE_URL"]
    }

    lateinit var dataSource: CommonDataSource

    lateinit var configuration: KotlinConfiguration

    lateinit var dataStore: KotlinEntityDataStore<Persistable>

    fun setup() {
        dataSource = when(AppConfig["DB_DRIVER"].toString().toLowerCase()) {
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

        configuration = KotlinConfiguration(
                dataSource = dataSource,
                model = Models.DEFAULT,
                useDefaultLogging = true
        ).apply {
            SchemaModifier(this).createTables(TableCreationMode.CREATE_NOT_EXISTS)
        }
        dataStore = KotlinEntityDataStore(configuration)
    }
}



fun main(args: Array<String>) {
    println(connectionUrl)
    println(Config.AppConfig["DB_USERNAME"])
    println(Config.AppConfig["DB_PASSWORD"])
    println(dataSource::class.java)
    val result = dataStore.select(Defect::class)()
    println(result)
}