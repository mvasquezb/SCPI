package com.pmvb.scpiback.data

import com.mysql.cj.jdbc.MysqlDataSource
import com.pmvb.scpiback.AppConfig
import com.pmvb.scpiback.data.models.Models
import io.requery.Persistable
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.SchemaModifier
import io.requery.sql.TableCreationMode

val connectionUrl = "jdbc:${AppConfig["DB_DRIVER"]}://${AppConfig["DB_HOST"]}:${AppConfig["DB_PORT"]}/" +
        "${AppConfig["DB_DATABASE"]}"

val source = when(AppConfig["DB_DRIVER"].toString().toLowerCase()) {
    "mysql" -> MysqlDataSource()
    else -> MysqlDataSource() // Default is MySQL as well, for now
}

val dataSource = source.apply {
    setUrl(connectionUrl)
    user = AppConfig["DB_USERNAME"].toString()
    password = AppConfig["DB_PASSWORD"].toString()
}

val configuration = KotlinConfiguration(
        dataSource = dataSource,
        model = Models.DEFAULT
).apply {
    SchemaModifier(this).createTables(TableCreationMode.CREATE_NOT_EXISTS)
}

val dataStore = KotlinEntityDataStore<Persistable>(configuration)