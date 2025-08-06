package com.om2.core


import com.zaxxer.hikari.HikariDataSource

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

// Importamos los objetos creados de tus modelos de dominio.
// Asegúrate de que estas importaciones sean correctas para la ubicación real de tus tablas.
import com.om2.modules.asignable.domain.*
import com.om2.modules.report.domain.*
import com.om2.modules.user.domain.*

/**
 * Objeto de ayuda para gestionar la conexión a la base de datos con Exposed.
 * Encapsula la inicialización y proporciona una función para transacciones asíncronas.
 */

object DatabaseFactory {

    /**
     * Inicializa la conexión de Exposed con el pool de conexiones de HikariCP
     * y crea las tablas de la base de datos si no existen.
     *
     * @param datasource La fuente de datos de HikariCP ya configurada.
     */

    fun init(datasource: HikariDataSource) {
        //Aqui conectamos el exposed a la database usando hikaridatasource

        val database = Database.connect(datasource)
        //Se ejecuta una transaccion para crear las tablas

        transaction(database) {
            // Corrección aquí: las tablas se pasan como argumentos separados por comas
            SchemaUtils.create(
                Area,
                Asignacion,
                CampoAsignacion,
                Infraestructura,
                Maquinaria,
                Objeto,
                TipoDanho,
                ReporteCorrectivo,
                ReportePreventivo,
                User,
                HistorialAccion
            )   //Fin SchemaUtils.create
        }   //Fin Transaction
    }       //Fin fun init
    //Fin DatabaseFactory


    /**
     * Función de utilidad para ejecutar operaciones de base de datos dentro de una transacción
     * suspendida, adecuada para el uso con corrutinas de Kotlin en Ktor.
     *
     * @param block El bloque de código que contiene las operaciones de base de datos.
     * @return El resultado del bloque de código.
     */
    suspend fun <T> dbQuery(block: suspend () -> T): T =
    // newSuspendedTransaction es parte del módulo experimental de Exposed para corrutinas.
        // Dispatchers.IO es el dispatcher recomendado para operaciones de I/O bloqueantes como las de BD.
        newSuspendedTransaction(Dispatchers.IO) { block() }
}