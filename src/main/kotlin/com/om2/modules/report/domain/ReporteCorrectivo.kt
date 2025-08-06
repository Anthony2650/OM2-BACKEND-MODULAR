package com.om2.modules.report.domain

// Importamos los objetos necesarios de las otras carpetas "domain":
import com.om2.modules.asignable.domain.Area
import com.om2.modules.asignable.domain.Objeto
import com.om2.modules.asignable.domain.TipoDanho
import com.om2.modules.user.domain.User

// Importamos módulos de la librería "Exposed":
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.ReferenceOption
import kotlinx.datetime.Instant


// Establecemos las etiquetas que pueden tener distintos atributos del objeto:
enum class Urgencia {
    BAJO,
    MEDIO,
    ALTO,
    CRITICO
}

enum class Estado {
    PENDIENTE,
    SEGUIMIENTO,
    DIAGNOSTICADO,
    FINALIZADO
}

object ReporteCorrectivo : Table("reportes_correctivos"){

    val id: Column<String> = varchar("id", 128)
    val usuarioId: Column<String> = varchar("user_id", 128)
        .references(User.id, onDelete = ReferenceOption.CASCADE)
    val areaId: Column<String> = varchar("area_id", 128)
        .references(Area.id, onDelete = ReferenceOption.SET_NULL)
    val objetoId: Column<String> = varchar("objeto_id", 128)
        .references(Objeto.id, onDelete = ReferenceOption.SET_NULL)
    val tipoDanhoId: Column<String> = varchar("tipo_danho_id", 128)
        .references(TipoDanho.id, onDelete = ReferenceOption.SET_NULL)
    val urgencia: Column<Urgencia> = customEnumeration(
        name = "urgencia",
        sql = "VARCHAR(50)",
        fromDb = { Urgencia.valueOf(it as String) },
        toDb = { it.name }
    )
    val observacion: Column<String> = varchar("observacion", 256)
    val imagenObservacion: Column<ByteArray?> = binary("imagen_observacion").nullable()
    val fechaHora: Column<Instant> = timestamp("fecha_hora").default(kotlinx.datetime.Clock.System.now())
    val estado: Column<Estado> = customEnumeration(
        name = "estado",
        sql = "VARCHAR(50)",
        fromDb = { Estado.valueOf(it as String) },
        toDb = { it.name }
    )
    val verificacion: Column<Boolean> = bool("verificacion")

    // CAMPOS OPCIONALES:
    val seguidoPor: Column<String?> = varchar("seguido_por", 128)
        .references(User.id, onDelete = ReferenceOption.SET_NULL).nullable()
    val diagnostico: Column<String?> = varchar("diagnostico", 256).nullable()
    val solucion: Column<String?> = varchar("solucion", 256).nullable()
    val imagenSolucion: Column<ByteArray?> = binary("imagen_solucion").nullable()
    val horaSeguimiento: Column<Instant?> = timestamp("hora_seguimiento").nullable()
    val horaDiagnostico: Column<Instant?> = timestamp("hora_diagnostico").nullable()
    val horaSolucion: Column<Instant?> = timestamp("hora_solucion").nullable()

    override val primaryKey = PrimaryKey(id)

}