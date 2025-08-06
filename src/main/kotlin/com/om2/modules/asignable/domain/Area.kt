package com.om2.modules.asignable.domain

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Area : Table("areas") {
    val id: Column<String> = varchar("id", 128)
    val nombre: Column<String> = varchar("nombre", 256).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}