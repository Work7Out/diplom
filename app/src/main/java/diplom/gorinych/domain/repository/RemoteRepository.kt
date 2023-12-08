package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.utils.Resource

interface RemoteRepository {
    suspend fun getLogin(login: String, password: String): Resource<User?>
}