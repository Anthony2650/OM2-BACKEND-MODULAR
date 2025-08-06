package com.om2.modules.user.presentation

import io.ktor.server.auth.*

import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

// Importamos los objetos necesarios:
import com.om2.modules.user.domain.serializables.LoginRequest

// Importamos los servicios necesarios:
import com.om2.modules.user.service.*

// Importamos los CRUD necesarios:
import com.om2.modules.user.data.*

import io.ktor.server.response.respondText


fun Route.loginRoute() {

    // Creamos variables para los servicios:
    val userCRUD = UserCRUD()
    val accessUserServices = AccesUserServices(userCRUD)

    route("api/") {
        post("/login") {

            //Convertimos los datos en un objeto de Kotlin:
            val request = call.receive<LoginRequest>()

            // Accedemos al servicio:
            val isValid = accessUserServices.login(request.username, request.password)
            if (isValid) {
                call.respondText("✅ El usuario tiene acceso exitosamente")
            } else {
                call.respondText("❌ El usuario no tiene acceso porque las credenciales ingresadas son incorrectas", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }

        }
    }


    authenticate("auth-jwt") {}

}


