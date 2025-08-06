package com.om2.app.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*

// Importamos los objetos creados dominio.
import com.om2.core.DatabaseFactory

/**
 * Función de extensión para la clase Application de Ktor.
 * Configura la conexión a la base de datos durante el inicio de la aplicación.
 */
fun Application.configureDatabase(){

    //Leer las configuraciones de la database desde application.conf
    val databaseUrl = environment.config.property("ktor.database.url").getString()
    val databaseUser = environment.config.property("ktor.database.user").getString()
    val databasePassword = environment.config.property("ktor.database.password").getString()

    // Configuración de HikariCP, el pool de conexiones de alto rendimiento.
    val config = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver" // Aqui definimos el driver JDBC para PostgreSQL
        jdbcUrl = databaseUrl
        username = databaseUser
        password = databasePassword
        maximumPoolSize = 3 // Número máximo de conexiones en el pool!
        isAutoCommit = false // Esta DESHABILITADO el auto-commit para gestionar transacciones manualmente con Exposed
        transactionIsolation = "TRANSACTION_REPEATABLE_READ" // Este es el nivel de aislamiento de la transacción
        validate() // Valida la configuración de HikariCP al inicio
    }
    val datasource = HikariDataSource(config) //Aqui creamos la fuente de datos de hikariCP

    // Inicializamos el pool de conexiones y creamos las tablas de la database
    DatabaseFactory.init(datasource)

}
