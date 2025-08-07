package com.om2.app.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.* // Asegúrate de que esta importación esté
import com.om2.modules.user.data.UserCRUD
import com.om2.modules.user.presentation.loginRoute // Importa tu función de rutas de login

/**
 * Configura las rutas de la aplicación.
 * Ahora acepta las dependencias necesarias para las rutas de usuario.
 */
fun Application.configureRouting(
    userCRUD: UserCRUD,
    jwtAudience: String,
    jwtDomain: String,
    jwtSecret: String
) {
    routing {
        // Ruta de bienvenida
        get("/") {
            call.respondText("¡Bienvenido a tu API Ktor!")
        }

        // Llama a las rutas específicas del módulo de usuario
        // Aquí es donde se conectan las rutas de login y protegidas.
        loginRoute(userCRUD, jwtAudience, jwtDomain, jwtSecret)
    }
}
