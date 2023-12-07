package diplom.gorinych.data.repository

import diplom.gorinych.data.db.AddonEntity
import diplom.gorinych.data.db.CallHistory
import diplom.gorinych.data.db.FeedBackEntity
import diplom.gorinych.data.db.HistoryEntity
import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.data.db.NoteEntity
import diplom.gorinych.data.db.PromoEntity
import diplom.gorinych.data.db.UserEntity
import diplom.gorinych.data.mapper.mapToAddon
import diplom.gorinych.data.mapper.mapToCall
import diplom.gorinych.data.mapper.mapToFeedBackEntity
import diplom.gorinych.data.mapper.mapToFeedback
import diplom.gorinych.data.mapper.mapToHistoryEntity
import diplom.gorinych.data.mapper.mapToHouseDetail
import diplom.gorinych.data.mapper.mapToHouses
import diplom.gorinych.data.mapper.mapToNote
import diplom.gorinych.data.mapper.mapToNoteEntity
import diplom.gorinych.data.mapper.mapToPromo
import diplom.gorinych.data.mapper.mapToPromoEntity
import diplom.gorinych.data.mapper.mapToReserve
import diplom.gorinych.data.mapper.mapToUser
import diplom.gorinych.data.mapper.mapToUserEntity
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.Promo
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun getAllHouses(): Flow<Resource<List<House>>> {
        return flow {
            try {
                val result = dao.getAllHouses()
                result.collect { houses ->
                    emit(Resource.Success(houses.mapToHouses()))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun getDetailHouse(
        idHouse: Int
    ): Resource<HouseDetail> {
        return try {
            val resultHouse = dao.getHouseById(idHouse)
            Resource.Success(resultHouse.mapToHouseDetail())
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getFeedbackByHouse(idHouse: Int): Flow<Resource<List<Feedback>>> {
        return flow {
            try {
                val resultFeedbacks = dao.getFeedBackByHouse(idHouse)
                resultFeedbacks.collect { feedbacks ->
                    emit(Resource.Success(feedbacks.map {
                        it.mapToFeedback()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }


    override suspend fun addReserve(
        idUser: Int,
        idHouse: Int,
        dateBegin: String,
        dateEnd: String,
        confirmReservation: String,
        amount: Double,
        addtions: String,
        dateCreate: String,
    ) {
        dao.insertReserve(
            HistoryEntity(
                idHouse = idHouse,
                idUser = idUser,
                amount = amount,
                confirmReservation = confirmReservation,
                dateBegin = dateBegin,
                dateEnd = dateEnd,
                dateCreate = dateCreate,
                additions = addtions
            )
        )
    }

    override suspend fun deleteReserve(reserve: Reserve) {
        dao.deleteReserve(reserve.mapToHistoryEntity())
    }

    override suspend fun insertFeedback(
        idUser: Int,
        idHouse: Int,
        dateCreate: String,
        content: String,
        isBlocked: Boolean,
        rang: Int
    ) {
        dao.insertFeedback(
            FeedBackEntity(
                idUser = idUser,
                idHouse = idHouse,
                content = content,
                isBlocked = isBlocked,
                dateFeedback = dateCreate,
                rang = rang
            )
        )
    }

    override suspend fun getReserveByUser(idUser: Int): Resource<List<Reserve>> {
        return try {
            val result = dao.getHistoryByUser(userId = idUser)
            Resource.Success(result.map {
                it.mapToReserve()
            })
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getAllUsers(): Flow<Resource<List<User>>> {
        return flow {
            try {
                val result = dao.getAllUsers()
                result.collect { users ->
                    emit(Resource.Success(users.map {
                        it.mapToUser()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateFeedback(feedback: Feedback) {
        dao.updateFeedback(feedback.mapToFeedBackEntity())
    }

    override suspend fun getAllFeedbacks(): Flow<Resource<List<Feedback>>> {
        return flow {
            try {
                val result = dao.getAllFeedBacks()
                result.collect {
                    emit(Resource.Success(it.map { feedBackEntity ->
                        feedBackEntity.mapToFeedback()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateHistory(reserve: Reserve) {
        dao.updateHistory(reserve.mapToHistoryEntity())
    }

    override suspend fun getAllHistory(): Flow<Resource<List<Reserve>>> {
        return flow {
            try {
                val result = dao.getAllHistory()
                result.collect { histories ->
                    emit(Resource.Success(histories.map {
                        it.mapToReserve()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun insertUser(
        name: String,
        password: String,
        phone: String,
        email: String,
        role: String,
        isBlocked: Boolean
    ) {
        dao.insertUser(
            UserEntity(
                name = name,
                password = password,
                phone = phone,
                isBlocked = isBlocked,
                role = role,
                email = email
            )
        )
    }

    override suspend fun getHistoryNoConfirmStatus(): Flow<Resource<List<Reserve>>> {
        return flow {
            try {
                val result = dao.getHistoryNoConfirmStatus()
                result.collect {
                    emit(Resource.Success(it.map { historyEntity ->
                        historyEntity.mapToReserve()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun addAddon(
        title: String,
        price: Double
    ) {
        dao.insertAddon(
            AddonEntity(
                title = title,
                price = price
            )
        )
    }

    override suspend fun getAddons(): Flow<Resource<List<Addon>>> {
        return flow {
            try {
                val result = dao.getAllAddons()
                result.collect {
                    emit(Resource.Success(it.map { entity ->
                        entity.mapToAddon()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun addPromo(
        valueDiscount: Int,
        description: String,
        isActive: Boolean
    ) {
        dao.insertPromo(
            PromoEntity(
                valueDiscount = valueDiscount,
                description = description,
                isActive = isActive
            )
        )
    }

    override suspend fun getPromos(): Flow<Resource<List<Promo>>> {
        return flow {
            try {
                val result = dao.getAllPromos()
                result.collect {
                    emit(Resource.Success(it.map { entity ->
                        entity.mapToPromo()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun addNote(
        title: String,
        content: String,
        dateCreate: String
    ) {
        dao.insertNews(
            NoteEntity(
                title = title,
                content = content,
                dateCreate = dateCreate
            )
        )
    }

    override suspend fun getNews(): Flow<Resource<List<Note>>> {
        return flow {
            try {
                val result = dao.getAllNews()
                result.collect {
                    emit(Resource.Success(it.map { entity ->
                        entity.mapToNote()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.mapToNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.mapToNoteEntity())
    }

    override suspend fun addCall(
        name: String,
        phone: String
    ) {
        dao.insertCall(
            CallHistory(
                name = name,
                phone = phone
            )
        )
    }

    override suspend fun getAllCalls(): Flow<Resource<List<Call>>> {
        return flow {
            try {
                val result = dao.getAllCalls()
                result.collect {
                    emit(Resource.Success(it.map { entity ->
                        entity.mapToCall()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun getHistoryByIdHouse(idHouse: Int): Flow<Resource<List<Reserve>>> {
        return flow {
            try {
                val result = dao.getHistoryByIdHouse(idHouse)
                result.collect {
                    emit(Resource.Success(it.map { entity ->
                        entity.mapToReserve()
                    }))
                }
            } catch (error: Exception) {
                emit(Resource.Error(error.localizedMessage ?: "Unknown error"))
            }
        }
    }

    override suspend fun updatePromo(promo: Promo) {
        dao.updatePromo(promo.mapToPromoEntity())
    }

    override suspend fun getPromoByName(query: String): Resource<Promo>? {
        return try {
            Resource.Success(dao.getPromoByName(query)?.mapToPromo())
        } catch (error: Exception) {
            Resource.Error(error.localizedMessage ?: "Unknown error")
        }
    }

}