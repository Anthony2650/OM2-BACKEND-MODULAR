package com.om2.modules.report.data

import com.om2.modules.report.domain.ReporteCorrectivo
import com.om2.modules.report.domain.Estado
import com.om2.modules.report.domain.ReporteFinalizado
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class ReporteFinalizado(
    val id: String,
    val usuarioId: String,
    val areaId: String,
    val objetoId: String,
    val tipoDanhoId: String,
    val urgencia: String,
    val observacion: String,
    val imagenObservacion: ByteArray?,
    val fechaHora: Instant,
    val estado: String,
    val verificacion: Boolean,
    val seguidoPor: String?,
    val diagnostico: String?,
    val solucion: String?,
    val imagenSolucion: ByteArray?,
    val horaSeguimiento: Instant?,
    val horaDiagnostico: Instant?,
    val horaSolucion: Instant?
)

fun obtenerReportesCorrectivosPendientes(): List<ReporteFinalizado> = transaction {
    ReporteCorrectivo.select { ReporteCorrectivo.estado eq Estado.PENDIENTE }
        .orderBy(ReporteCorrectivo.fechaHora to SortOrder.DESC)
        .map {
            ReporteFinalizado(
                id = it[ReporteCorrectivo.id],
                usuarioId = it[ReporteCorrectivo.usuarioId],
                areaId = it[ReporteCorrectivo.areaId],
                objetoId = it[ReporteCorrectivo.objetoId],
                tipoDanhoId = it[ReporteCorrectivo.tipoDanhoId],
                urgencia = it[ReporteCorrectivo.urgencia].name,
                observacion = it[ReporteCorrectivo.observacion],
                imagenObservacion = it[ReporteCorrectivo.imagenObservacion],
                fechaHora = it[ReporteCorrectivo.fechaHora],
                estado = it[ReporteCorrectivo.estado].name,
                verificacion = it[ReporteCorrectivo.verificacion],
                seguidoPor = it[ReporteCorrectivo.seguidoPor],
                diagnostico = it[ReporteCorrectivo.diagnostico],
                solucion = it[ReporteCorrectivo.solucion],
                imagenSolucion = it[ReporteCorrectivo.imagenSolucion],
                horaSeguimiento = it[ReporteCorrectivo.horaSeguimiento],
                horaDiagnostico = it[ReporteCorrectivo.horaDiagnostico],
                horaSolucion = it[ReporteCorrectivo.horaSolucion]
            )
        }
}