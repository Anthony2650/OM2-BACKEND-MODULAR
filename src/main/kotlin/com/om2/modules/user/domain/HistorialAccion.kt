package com.om2.modules.user.domain

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.ReferenceOption
import kotlinx.datetime.Instant

// Establecemos las principales acciones que el usuario puede hacer en la aplicación:
enum class TipoAccion {
    LOGIN_EXITOSO,
    LOGIN_FALLIDO,
    LOGOUT,
    CAMBIO_CONTRASENA,
    REINICIO_CONTRASENA,
    CREAR_REPORTE,
    ACTUALIZAR_REPORTE,
    ELIMINAR_REPORTE,
    ASIGNAR_REPORTE,
    CAMBIAR_ESTADO_REPORTE,
    ADJUNTAR_IMAGEN_REPORTE,
    CREAR_USUARIO,
    ACTUALIZAR_USUARIO,
    ELIMINAR_USUARIO,
    CAMBIAR_CONFIGURACION,
    GENERAR_REPORTE_PDF
}

object HistorialAccion : Table("historial_acciones") {

    val id: Column<String> = varchar("id", 128)
    val usuarioId: Column<String> = varchar("user_id", 128).references(User.id)
    val rol: Column<String> = varchar("rol", 128)
    val tipoAccion: Column<TipoAccion> = customEnumeration(
        name = "tipo_accion",
        sql = "VARCHAR(70)",
        fromDb = { TipoAccion.valueOf(it as String) },
        toDb = { it.name }
    )
    val timestamp: Column<Instant> = timestamp("timestamp")


}