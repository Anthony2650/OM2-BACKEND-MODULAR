package com.om2.modules.asignable.domain

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object Objeto : Table("objetos") {

    val id: Column<String> = varchar("id", 128)
    val nombre: Column<String> = varchar("nombre", 256).uniqueIndex()
    val id_area: Column<String> = varchar("descripcion", 512)
        .references(Area.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}

object TipoDanho : Table("tipos_danhos") {

    val id: Column<String> = varchar("id", 128)
    val nombre: Column<String> = varchar("nombre", 128)

    override val primaryKey = PrimaryKey(id)

}