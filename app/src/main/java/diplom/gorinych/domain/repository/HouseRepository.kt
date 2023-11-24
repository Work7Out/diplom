package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.utils.Resource

interface HouseRepository {
    suspend fun login(login: String, password: String): Resource<User?>
    suspend fun getUserById(userId: Int): Resource<User>
}