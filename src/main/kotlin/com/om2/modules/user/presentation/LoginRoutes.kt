package com.om2.modules.user.presentation

import io.ktor.server.auth.*
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.response.respondText
import io.ktor.http.HttpStatusCode
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import io.ktor.server.application.call
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.response.respond
import com.om2.modules.user.domain.serializables.LoginRequest
import com.om2.modules.user.service.AccesUserServices
import com.om2.modules.user.data.UserCRUD
import io.ktor.server.routing.get



/**
 * Define las rutas relacionadas con la autenticación de usuarios y las rutas protegidas.
 * Esta función ahora acepta los parámetros necesarios para la configuración de JWT
 * y el repositorio de usuarios.
 */
fun Route.loginRoute(
    userCRUD: UserCRUD,
    jwtAudience: String,
    jwtDomain: String,
    jwtSecret: String
) {
    // Creamos una única instancia del servicio, inyectando el UserCRUD.
    val accessUserServices = AccesUserServices(userCRUD)

    route("api/") {
        post("/login") {

            // Convierte los datos de la petición en un objeto de Kotlin:
            val request = call.receive<LoginRequest>()


            // Accede al servicio para validar las credenciales.


            val isValid = accessUserServices.login(request.username, request.password)
            // La función de arriba devuelve un Boolean.


            if (isValid) {
                // SI EL LOGIN ES VÁLIDO, NECESITAMOS VOLVER A BUSCAR EL USUARIO
                // PARA OBTENER SUS DATOS Y CREAR EL TOKEN JWT.
                val user = userCRUD.findUserByUsername(request.username)

                if (user != null) {
                    // Si el usuario se encuentra entonces generamos el token JWT.
                    val token = JWT.create()
                        .withAudience(jwtAudience)
                        .withIssuer(jwtDomain)
                        .withClaim("username", user.username) // Aqui usamos el nombre de usuario en el token
                        .withExpiresAt(Date(System.currentTimeMillis() + 600000)) // Expira en 10 minutos
                        .sign(Algorithm.HMAC256(jwtSecret))

                    // Respondemos con el token JWT.
                    call.respond(mapOf("token" to token))
                } else {
                    // Esto no debería pasar si isValid es true pero es un caso de seguridad.
                    call.respond(HttpStatusCode.InternalServerError, "Error interno: Usuario no encontrado después de login exitoso.")
                }
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Credenciales incorrectas")
            }
        }
    }

}


