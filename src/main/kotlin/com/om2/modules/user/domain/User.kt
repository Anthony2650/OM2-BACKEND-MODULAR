package com.om2.modules.user.domain

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

// Establecemos las etiquetas que puede tener el campo de "rol":
enum class Rol {
    REPORTADOR,
    EJECUTOR,
    JEFE_AREA,
    ADMINISTRADOR
}

object User : Table("users") {

    val id: Column<String> = varchar("id", 128)
    val username: Column<String> = varchar("username", 256).uniqueIndex()
    val nombreCompleto: Column<String> = varchar("nombre_completo", 256)
    val password: Column<String> = varchar("password", 256)
    val rol: Column<Rol> = customEnumeration(
        name = "rol",
        sql = "VARCHAR(70)",
        fromDb = { Rol.valueOf(it as String) },
        toDb = { it.name }
    )
    val identificacion: Column<String> = varchar("identificacion", 128)
    val direccion: Column<String> = varchar("direccion", 256)
    val telefono: Column<String> = varchar("telefono", 128)
    val disponible: Column<Boolean> = bool("disponible")
    val activo: Column<Boolean> = bool("activo")

    override val primaryKey = PrimaryKey(id)
}