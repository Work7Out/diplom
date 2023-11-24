package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.utils.Resource

interface HouseRepository {
    suspend fun login(login: String, password: String): Resource<User?>
    suspend fun getUserById(userId: Int): Resource<User>
    suspend fun updateUser(user: User)
    suspend fun getAllHouses(): Resource<List<House>>
}