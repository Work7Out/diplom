package diplom.gorinych.data.repository

import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.data.mapper.mapToHouses
import diplom.gorinych.data.mapper.mapToUser
import diplom.gorinych.data.mapper.mapToUserEntity
import diplom.gorinych.domain.model.House
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
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getUserById(
        userId: Int
    ): Resource<User> {
        return try {
            val result = dao.getUserById(
                userId = userId
            )
            Resource.Success(result.mapToUser())
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun updateUser(user: User) {
        dao.updateUserEntity(user.mapToUserEntity())
    }

    override suspend fun getAllHouses(): Resource<List<House>> {
        return try {
            val result = dao.getAllHouses()
            Resource.Success(result.mapToHouses())
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }
}