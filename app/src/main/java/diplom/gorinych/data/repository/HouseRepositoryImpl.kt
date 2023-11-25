package diplom.gorinych.data.repository

import diplom.gorinych.data.db.HistoryEntity
import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.data.mapper.mapToFeedBackEntity
import diplom.gorinych.data.mapper.mapToFeedback
import diplom.gorinych.data.mapper.mapToHistoryEntity
import diplom.gorinych.data.mapper.mapToHouseDetail
import diplom.gorinych.data.mapper.mapToHouses
import diplom.gorinych.data.mapper.mapToUser
import diplom.gorinych.data.mapper.mapToUserEntity
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Reserve
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

    override suspend fun getDetailHouse(
        idHouse: Int
    ): Resource<HouseDetail> {
        return try {
            val resultHouse = dao.getHouseById(idHouse)
            val resultFeedbacks = dao.getFeedBackByHouse(idHouse)
            Resource.Success(mapToHouseDetail(
                houseEntity = resultHouse,
                feedbacks = resultFeedbacks.map { it.mapToFeedback() }
            ))
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun addReserve(
        idUser: Int,
        idHouse: Int,
        dateBegin: String,
        dateEnd: String,
        confirmReservation: String,
        amount: Double,
        dateCreate: String
    ) {
        dao.insertReserve(HistoryEntity(
            idHouse = idHouse,
            idUser = idUser,
            amount = amount,
            confirmReservation = confirmReservation,
            dateBegin = dateBegin,
            dateEnd = dateEnd,
            dateCreate = dateCreate
        ))
    }

    override suspend fun deleteReserve(reserve: Reserve) {
        dao.deleteReserve(reserve.mapToHistoryEntity())
    }

    override suspend fun insertFeedback(feedback: Feedback) {
        dao.insertFeedback(feedback.mapToFeedBackEntity())
    }

}