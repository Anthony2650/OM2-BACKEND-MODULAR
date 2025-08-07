package com.om2.app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    /**
     * Configura el plugin de Content Negotiation para manejar la serialización/deserialización JSON.
     * Las rutas asociadas a JSON deben definirse en el módulo de routing.
     */

        install(ContentNegotiation) {
            json()
        }
        // Debería ir en configureRouting() o en un archivo de rutas específico.
}


