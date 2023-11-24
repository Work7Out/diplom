package diplom.gorinych.data.repository

import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.data.mapper.mapToUser
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import javax.inject.Inject

class HouseRepositoryImpl @Inject constructor(
    db: HouseBotDatabase
) : HouseRepository {
    private val dao = db.houseDao
    override suspend fun login(
        login: String,
        password: String
    ): Resource<User?> {
        return try {
            val result = dao.getUserByLoginAndPassword(
                login = login,
                password = password
            )
            Resource.Success(result?.mapToUser())
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage?: "Unknown error")
        }
    }
}