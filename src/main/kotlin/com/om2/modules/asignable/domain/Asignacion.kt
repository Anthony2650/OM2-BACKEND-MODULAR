package com.om2.modules.asignable.domain

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.ReferenceOption

object Asignacion : Table("asignaciones"){

    val id: Column<String> = varchar("id", 128)
    val nombre: Column<String> = varchar("nombre", 256)
    val tipoAsignacion: Column<String> = varchar("tipo_asignacion", 256)
    val nombreTipoAsignacion: Column<String> = varchar("nombre_tipoasignacion", 128)
    val tiempoEstimado: Column<Int> = integer("tiempo_estimado")

    override val primaryKey = PrimaryKey(id)

}