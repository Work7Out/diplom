package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.Promo
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HouseRepository {
    suspend fun login(login: String, password: String): Resource<User?>
    suspend fun getUserById(userId: Int): Resource<User>
    suspend fun updateUser(user: User)
    suspend fun getAllHouses(): Flow<Resource<List<House>>>
    suspend fun getDetailHouse(idHouse: Int): Resource<HouseDetail>
    suspend fun deleteReserve(reserve: Reserve)
    suspend fun addReserve(
        idUser: Int,
        idHouse: Int,
        dateBegin: String,
        dateEnd: String,
        confirmReservation: String,
        amount: Double,
        addtions: String,
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
    suspend fun getAllUsers(): Flow<Resource<List<User>>>
    suspend fun updateFeedback(feedback: Feedback)
    suspend fun getAllFeedbacks(): Flow<Resource<List<Feedback>>>
    suspend fun updateHistory(reserve: Reserve)
    suspend fun getAllHistory(): Flow<Resource<List<Reserve>>>
    suspend fun insertUser(
        name: String,
        password: String,
        phone: String,
        email: String,
        role: String,
        isBlocked: Boolean
    )

    suspend fun getHistoryNoConfirmStatus(): Flow<Resource<List<Reserve>>>
    suspend fun addAddon(title: String, price: Double)
    suspend fun getAddons(): Flow<Resource<List<Addon>>>
    suspend fun addPromo(valueDiscount: Int, description: String, isActive: Boolean)
    suspend fun getPromos(): Flow<Resource<List<Promo>>>
    suspend fun addNote(title: String, content: String, dateCreate: String)
    suspend fun getNews(): Flow<Resource<List<Note>>>
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun addCall(name: String, phone: String)
    suspend fun getAllCalls(): Flow<Resource<List<Call>>>
    suspend fun getHistoryByIdHouse(idHouse: Int): Flow<Resource<List<Reserve>>>
    suspend fun getFeedbackByHouse(idHouse: Int): Flow<Resource<List<Feedback>>>
}