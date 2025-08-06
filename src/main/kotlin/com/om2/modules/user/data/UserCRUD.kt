package com.om2.modules.user.data

import com.om2.core.DatabaseFactory.dbQuery
import com.om2.modules.user.domain.Rol
import com.om2.modules.user.domain.User
import org.jetbrains.exposed.sql.*

// Clase para representar los datos del usuario:
data class UserDTO(
    val id: String,
    val username: String,
    val nombreCompleto: String,
    val password: String, //hashear
    val rol: Rol,
    val identificacion: String,
    val direccion: String,
    val telefono: String,
    val disponible: Boolean,
    val activo: Boolean
)

// Consultas CRUD relacionadas con la tabla "Users":
class UserCRUD {

    // ======================
    // CONSULTAR USUARIOS:
    // ======================

    // Consultar todos los usuarios:
    suspend fun getAllUsers(): List<UserDTO> = dbQuery {
        User.selectAll().map(this::toUserDTO)
    }

    // Consultar usuario por su ID:
    suspend fun getUserById(id:String): UserDTO? = dbQuery {
        User.select {User.id eq id}
            .mapNotNull(this::toUserDTO)
            .singleOrNull()
    }

    // Consultar usuario por su Username:
    suspend fun findUserByUsername(username: String): UserDTO? = dbQuery {
        User.select {User.username eq username}
            .mapNotNull(this::toUserDTO)
            .singleOrNull()
    }


    //Crea un nuevo usuario en la base de datos
    //@param UserDTO se usa el UserDTO con los datos del nuevo usuario
    //@return un objeto UserDTO del usuario creado
    suspend fun createUser(userDTO: UserDTO): UserDTO = dbQuery {
        val insertStatement = User.insert {
            it[id] = userDTO.id
            it[username] = userDTO.username
            it[nombreCompleto] = userDTO.nombreCompleto
            it[password] = userDTO.password
            it[rol] = userDTO.rol
            it[identificacion] = userDTO.identificacion
            it[direccion] = userDTO.direccion
            it[telefono] = userDTO.telefono
            it[disponible] = userDTO.disponible
            it[activo] = userDTO.activo
        }
        insertStatement.resultedValues?.firstOrNull()?.let { toUserDTO(it) }
            ?: throw IllegalStateException("Error al crear usuario")
    }

    //Actualiza un usuario por su id
    //@param el id del usuario
    //@param UserDTO el objeto UserDTO con los campos a actualizar
    //@return True si se actualizo el usuario, false en caso contrario
    suspend fun updateUser(id: String, userDTO: UserDTO): Boolean = dbQuery {
        User.update({ User.id eq id }) {
            it[username] = userDTO.username
            it[nombreCompleto] = userDTO.nombreCompleto
            it[password] = userDTO.password // ¡Recuerda hashear la contraseña aquí si se actualiza!
            it[rol] = userDTO.rol
            it[identificacion] = userDTO.identificacion
            it[direccion] = userDTO.direccion
            it[telefono] = userDTO.telefono
            it[disponible] = userDTO.disponible
            it[activo] = userDTO.activo
        } > 0
    }


    //Elimina un usuario por su ID
    //@param id del usuario a eliminar
    //@return True si se elimino el usuario, False en caso contrario
    // suspend fun deleteUser(id: String): Boolean = dbQuery {
        // User.deleteWhere { User.id.eq(id) } > 0
    //}



    private fun toUserDTO(row: ResultRow): UserDTO {
        return UserDTO(
            id = row[User.id],
            username = row[User.username],
            nombreCompleto = row[User.nombreCompleto],
            password = row[User.password],
            rol = row[User.rol],
            identificacion = row[User.identificacion],
            direccion = row[User.direccion],
            telefono = row[User.telefono],
            disponible = row[User.disponible],
            activo = row[User.activo]
        )
    }
}