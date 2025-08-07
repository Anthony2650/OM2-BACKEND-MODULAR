package com.example.com.om2.app

//importaciones de los plugins
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

// Importaciones de los módulos
import com.om2.app.plugins.configureDatabase
import com.om2.app.plugins.configureHTTP
import com.om2.app.plugins.configureSecurity
import com.om2.app.plugins.configureSerialization
import com.om2.app.plugins.configureRouting
import com.om2.modules.user.data.UserCRUD
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    // Punto de entrada principal para iniciar el servidor Ktor usando Netty.
    io.ktor.server.netty.EngineMain.main(args)
}

/**
 * La función 'module' es el punto de entrada de configuración de tu aplicación Ktor.
 * Aquí se instalan los plugins y se definen las dependencias y rutas.
 */
fun Application.module() {
    // 1. Configuración de Serialización (JSON)
    // Llama a la función que instala ContentNegotiation.
    configureSerialization()

    // 2. Configuración HTTP (CORS)
    // Llama a la función que instala CORS.
    configureHTTP()

    // 3. Configuración de la Base de Datos
    // Llama a la función que inicializa Exposed, HikariCP y crea las tablas de la BD.
    configureDatabase()

    // ====================================================================
    // 4. Configuración de Autenticación JWT
    // ====================================================================
    // Lee las propiedades de JWT desde tu archivo application.yaml.
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtDomain = environment.config.property("jwt.domain").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()

    // Llama a tu función 'configureSecurity' para instalar y configurar el plugin JWT.
    // Esta función ahora recibe los parámetros JWT que acabamos de leer.
    configureSecurity(jwtAudience, jwtDomain, jwtRealm, jwtSecret)


    // ====================================================================
    // 5. Configuración de Rutas
    // ====================================================================
    // Crea una instancia de tu UserRepository.
    val userCRUD = UserCRUD()

    // Llama a tu función 'configureRouting' para definir todas las rutas de tu API.
    // Le pasamos el UserRepository y los parámetros JWT para que las rutas puedan utilizarlos.
    configureRouting(userCRUD, jwtAudience, jwtDomain, jwtSecret)
}