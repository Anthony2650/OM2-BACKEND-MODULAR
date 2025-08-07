package com.om2.modules.user.service

// Importamos las consultas CRUD necesarias:
import com.om2.modules.user.data.UserCRUD

class AccesUserServices(private val userCRUD: UserCRUD) {

    // Función para el inicio de sesión de un usuario:
    suspend fun login(username: String, password: String): Boolean {

        // Consultamos al usuario con el nombre de usuario ingresado:
        val user = userCRUD.findUserByUsername(username)

        //Validamos sis e encontró un usuario:
        if (user != null) {
            println("✅ Se encontró un usuario relacionado al username $username.")

            // Validamos si la contraseña es correcta:
            if (user.password == password) {
                return true

            }

            else {
                println("❌ La contraseña es incorrecta para el usuario $username. \n")
                return false
            }

        }

        else {
            println("❌ No se encontró un usuario con el username $username. \n")
            return false
        }

    }
}
