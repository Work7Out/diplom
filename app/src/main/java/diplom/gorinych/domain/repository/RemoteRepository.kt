package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.utils.Resource

interface RemoteRepository {
    suspend fun getLogin(login: String, password: String): Resource<User?>
    suspend fun getAllUsers(): Resource<List<User>>
    suspend fun addNewUser(
        name: String,
        password: String,
        phone: String,
        email: String,
        role: String,
        isBlocked: Boolean
    )

    suspend fun getHistoryByStatus(status: String): Resource<List<Reserve>>
    suspend fun updateUser(user: User)
}