package com.om2.modules.asignable.domain

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.ReferenceOption

object Maquinaria : Table("maquinarias"){

    val id: Column<String> = varchar("id", 128)
    val nombre: Column<String> = varchar("nombre", 256)
    val area: Column<String> = varchar("area", 256)
        .references(Area.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}