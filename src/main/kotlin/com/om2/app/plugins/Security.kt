package com.om2.app.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond

/**
 * Configura el plugin de seguridad (Authentication) de Ktor, incluyendo la autenticación JWT.
 *
 * @param jwtAudience La audiencia JWT configurada desde application.yaml.
 * @param jwtDomain El dominio JWT configurado desde application.yaml.
 * @param jwtRealm El reino JWT configurado desde application.yaml.
 * @param jwtSecret La clave secreta JWT configurada desde application.yaml.
 */
fun Application.configureSecurity(
    jwtAudience: String,
    jwtDomain: String,
    jwtRealm: String,
    jwtSecret: String
) {
    install(Authentication) {
        jwt("auth-jwt") { // Define un proveedor de autenticación JWT llamado "auth-jwt"
            realm = jwtRealm
            verifier( // Configura el verificador de JWT
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential -> // Lógica para validar el payload del token
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload) // Si es válido, devuelve un Principal de JWT
                } else {
                    null // Si no es válido, devuelve null
                }
            }
            challenge { defaultScheme, realm -> // Lógica para responder cuando la autenticación JWT falla
                call.respond(HttpStatusCode.Unauthorized, "Token no válido o expirado")
            }
        }
    }
}
