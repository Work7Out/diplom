package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.utils.Resource

interface HouseRepository {
    suspend fun login(login: String, password: String): Resource<User?>
    suspend fun getUserById(userId: Int): Resource<User>
    suspend fun updateUser(user: User)
    suspend fun getAllHouses(): Resource<List<House>>
    suspend fun getDetailHouse(idHouse: Int): Resource<HouseDetail>
    suspend fun deleteReserve(reserve: Reserve)
    suspend fun addReserve(
        idUser: Int,
        idHouse: Int,
        dateBegin: String,
        dateEnd: String,
        confirmReservation: String,
        amount: Double,
        dateCreate: String
    )

    suspend fun insertFeedback(
        idUser: Int,
        idHouse: Int,
        dateCreate: String,
        content:String,
        isBlocked:Boolean,
        rang:Int
    )

    suspend fun getReserveByUser(idUser: Int): Resource<List<Reserve>>
    suspend fun getAllUsers(): Resource<List<User>>
    suspend fun updateFeedback(feedback: Feedback)
    suspend fun getAllFeedbacks(): Resource<List<Feedback>>
    suspend fun updateHistory(reserve: Reserve)
    suspend fun getAllHistory(): Resource<List<Reserve>>
    suspend fun insertUser(
        name: String,
        password: String,
        phone: String,
        email: String,
        role: String,
        isBlocked: Boolean
    )
}