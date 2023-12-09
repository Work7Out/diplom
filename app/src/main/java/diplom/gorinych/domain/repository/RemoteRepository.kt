package diplom.gorinych.domain.repository

import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.Promo
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
    suspend fun addNewAddon(title: String, price: Double)
    suspend fun addNewPromo(description: String, valueDiscount: Int, isActive: Boolean)
    suspend fun getAllAddons(): Resource<List<Addon>>
    suspend fun getAllPromos(): Resource<List<Promo>>
    suspend fun getAllHistory(): Resource<List<Reserve>>
    suspend fun getAllFeedbacks(): Resource<List<Feedback>>
    suspend fun updateFeedback(feedback: Feedback)
    suspend fun updateHistory(reserve: Reserve)
    suspend fun getUserBiId(idUser: Int): Resource<User>
    suspend fun getAllNews(): Resource<List<Note>>
    suspend fun addNewNews(title: String, content: String, dateCreate: String)
    suspend fun updateNews(note: Note)
    suspend fun deleteNews(newsId: Int)
    suspend fun addNewCall(name: String, phone: String)
    suspend fun getAllCalls(): Resource<List<Call>>
    suspend fun getAllHouses(): Resource<List<House>>
    suspend fun getHistoryByUser(userId: Int): Resource<List<Reserve>>
    suspend fun deleteHistory(historyId: Int)
}