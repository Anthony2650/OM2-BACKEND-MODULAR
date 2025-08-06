package com.om2.modules.report.domain

// Importamos los objetos necesarios creados en otros "domain":
import com.asyncapi.kotlinasyncapi.annotation.Boolean
import com.om2.modules.user.domain.*
import com.om2.modules.asignable.domain.*

// Importamos módulos de la librería "Exposed":
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.ReferenceOption
import kotlinx.datetime.Instant

// Establecemos las etiquetas que pueden tener distintos atributos del objeto:
enum class TipoPeriodicidad {
    DIARIO,
    SEMANAL,
    MENSUAL,
    ANUAL
}

object ReportePreventivo : Table("reportes_preventivos") {

    val id: Column<String> = varchar("id", 128)
    val usuarioId: Column<String> = varchar("user_id", 128)
        .references(User.id, onDelete = ReferenceOption.CASCADE)
    val areaId: Column<String> = varchar("area_id", 128)
        .references(Area.id, onDelete = ReferenceOption.SET_NULL)
    val asignacionId: Column<String> = varchar("asignacion_id", 128)
        .references(Asignacion.id, onDelete = ReferenceOption.SET_NULL)
    val fechaInicio: Column<Instant> = timestamp("fecha_inicio")
    val tipoPeriodicidad: Column<TipoPeriodicidad?> = customEnumeration(
        name = "tipo_periodicidad",
        sql = "VARCHAR(50)",
        fromDb = { TipoPeriodicidad.valueOf(it as String) },
        toDb = { it.name }
    ).nullable()
    val periodicidad: Column<Int?> = integer("periodicidad").nullable()
    val diasSemana: Column<String?> = varchar("dias_semana", 100).nullable()
    val fechaFin: Column<Instant> = timestamp("fecha_fin")
    val materiales: Column<String> = text("materiales")
    val medidasSeguridad: Column<String> = text("medidas_seguridad")
    val observacion: Column<String?> = text("observacion").nullable()
    val horaInicio: Column<Instant> = timestamp("hora_inicio").default(kotlinx.datetime.Clock.System.now())
    val horaFin: Column<Instant> = timestamp("hora_fin").default(kotlinx.datetime.Clock.System.now())
    val confirmada: Column<kotlin.Boolean> = bool("confirmada")

    override val primaryKey = PrimaryKey(id)

}