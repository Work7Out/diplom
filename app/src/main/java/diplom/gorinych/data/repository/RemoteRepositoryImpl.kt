package diplom.gorinych.data.repository

import android.util.Log
import diplom.gorinych.data.mapper.mapDtoToUser
import diplom.gorinych.data.mapper.mapDtoToUserNull
import diplom.gorinych.data.mapper.mapFromDtoToAddon
import diplom.gorinych.data.mapper.mapFromDtoToCall
import diplom.gorinych.data.mapper.mapFromDtoToFeedback
import diplom.gorinych.data.mapper.mapFromDtoToHouse
import diplom.gorinych.data.mapper.mapFromDtoToHouseDetail
import diplom.gorinych.data.mapper.mapFromDtoToNote
import diplom.gorinych.data.mapper.mapFromDtoToPromo
import diplom.gorinych.data.mapper.mapFromDtoToPromoIsNull
import diplom.gorinych.data.mapper.mapFromDtoToReserve
import diplom.gorinych.data.remote.HouseBoatApi
import diplom.gorinych.data.remote.body_dto.AddAddonBody
import diplom.gorinych.data.remote.body_dto.AddCallBody
import diplom.gorinych.data.remote.body_dto.AddFeedbackBody
import diplom.gorinych.data.remote.body_dto.AddHistoryBody
import diplom.gorinych.data.remote.body_dto.AddNewsBody
import diplom.gorinych.data.remote.body_dto.AddPromoBody
import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
import diplom.gorinych.data.remote.body_dto.UpdateFeedbackBody
import diplom.gorinych.data.remote.body_dto.UpdateHistoryBody
import diplom.gorinych.data.remote.body_dto.UpdateNewsBody
import diplom.gorinych.data.remote.body_dto.UpdatePromoBody
import diplom.gorinych.data.remote.body_dto.UpdateUserBody
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.Promo
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.Resource
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val diplomaApi: HouseBoatApi
) : RemoteRepository {

    override suspend fun getLogin(
        login: String,
        password: String
    ): Resource<User?> {
        return Resource.handleResponse {
            diplomaApi.login(
                LoginBody(
                    login = login,
                    password = password
                )
            )?.mapDtoToUserNull()
        }
    }

    override suspend fun getAllUsers(): Resource<List<User>> {
        return Resource.handleResponse {
            diplomaApi.getAllUsers().map { it.mapDtoToUser() }
        }
    }

    override suspend fun getUserBiId(idUser:Int): Resource<User> {
        return Resource.handleResponse {
            diplomaApi.getUserById(userId = idUser).mapDtoToUser()
        }
    }

    override suspend fun addNewUser(
        name: String,
        password: String,
        phone: String,
        email: String,
        role: String,
        isBlocked: Boolean
    ) {
        Resource.handleResponse {
            diplomaApi.registration(
                RegistrationBody(
                    name = name,
                    password = password,
                    phone = phone,
                    eMail = email,
                    role = role,
                    isBlocked = isBlocked
                )
            )
        }
    }

    override suspend fun getHistoryByStatus(status: String): Resource<List<Reserve>> {
        return Resource.handleResponse {
            diplomaApi.getHistoryByStatus(
                status = status
            ).map { it.mapFromDtoToReserve() }
        }
    }

    override suspend fun addNewHistory(
        idUser:Int,
        idHouse: Int,
        amount: Double,
        confirmReservation: String,
        additions: String,
        dataBegin: String,
        dataEnd: String,
        dateCreate: String,
    ) {
        Resource.handleResponse {
            diplomaApi.addNewHistory(
                AddHistoryBody(
                    idUser = idUser,
                    idHouse = idHouse,
                    amount = amount,
                    confirmReservation = confirmReservation,
                    additions = additions,
                    dataBegin = dataBegin,
                    dataEnd = dataEnd,
                    dateCreate = dateCreate
                )
            )
        }
    }

    override suspend fun getHistoryByUser(userId: Int): Resource<List<Reserve>> {
        return Resource.handleResponse {
            diplomaApi.getHistoryByUser(userId).map { it.mapFromDtoToReserve() }
        }
    }

    override suspend fun deleteHistory(
        historyId: Int
    ) {
        Resource.handleResponse {
            diplomaApi.deleteHistory(historyId)
        }
    }

    override suspend fun updateUser(
        user:User
    ) {
        Resource.handleResponse {
            diplomaApi.updateUser(
                UpdateUserBody(
                    id = user.id,
                    name = user.name,
                    password = user.password,
                    phone = user.phone,
                    eMail = user.email,
                    role = user.role,
                    isBlocked = user.isBlocked
                )
            )
        }
    }



    override suspend fun addNewAddon(
        title: String,
        price: Double
    ) {
        Resource.handleResponse {
            diplomaApi.addAddon(
                AddAddonBody(
                    title = title,
                    price = price,
                )
            )
        }
    }

    override suspend fun addNewPromo(
        description: String,
        valueDiscount: Int,
        isActive: Boolean
    ) {
        Resource.handleResponse {
            diplomaApi.addPromo(
                AddPromoBody(
                    description = description,
                    valueDiscount = valueDiscount,
                    isActive = isActive
                )
            )
        }
    }

    override suspend fun updatePromo(
        id: Int,
        description: String,
        valueDiscount: Int,
        isActive: Boolean
    ) {
        Resource.handleResponse {
            diplomaApi.updatePromo(
                UpdatePromoBody(
                    id = id,
                    description = description,
                    valueDiscount = valueDiscount,
                    isActive = isActive
                )
            )
        }
    }

    override suspend fun getAllAddons(): Resource<List<Addon>> {
        return Resource.handleResponse {
            diplomaApi.getAllAddons().map { it.mapFromDtoToAddon() }
        }
    }

    override suspend fun getAllPromos(): Resource<List<Promo>> {
        return Resource.handleResponse {
            diplomaApi.getAllPromos().map { it.mapFromDtoToPromo() }
        }
    }

    override suspend fun getPromoByDescription(query: String): Resource<Promo?> {
        return Resource.handleResponse {
            diplomaApi.getPromosByDescription(query).mapFromDtoToPromoIsNull()
        }
    }

    override suspend fun getAllHistory(): Resource<List<Reserve>> {
        return Resource.handleResponse {
            diplomaApi.getAllHistory().map {
                Log.d("find error",  "$it")
                it.mapFromDtoToReserve() }
        }
    }

    override suspend fun getHistoryByHouse(houseId: Int): Resource<List<Reserve>> {
        return Resource.handleResponse {
            diplomaApi.getHistoryByHouse(houseId).map { it.mapFromDtoToReserve() }
        }
    }

    override suspend fun updateHistory(reserve: Reserve) {
        Resource.handleResponse {
            diplomaApi.updateHistory(
                UpdateHistoryBody(
                    id = reserve.id,
                    dateCreate = reserve.dateCreate,
                    idHouse = reserve.idHouse,
                    idUser = reserve.idUser,
                    additions = reserve.additions,
                    amount = reserve.amount,
                    confirmReservation = reserve.confirmReservation,
                    dataBegin = reserve.dateBegin,
                    dataEnd = reserve.dateEnd
                )
            )
        }
    }

    override suspend fun getAllFeedbacks(): Resource<List<Feedback>> {
        return Resource.handleResponse {
            diplomaApi.getAllFeedbacks().map { it.mapFromDtoToFeedback() }
        }
    }

    override suspend fun getFeedbacksByHouse(houseId: Int): Resource<List<Feedback>> {
        return Resource.handleResponse {
            diplomaApi.getFeedbacksByHouse(houseId).map { it.mapFromDtoToFeedback() }
        }
    }

    override suspend fun addNewFeedback(
        idUser: Int,
        idHouse: Int,
        dateFeedback: String,
        content: String,
        isBlocked: Boolean,
        rang: Int
    ) {
        Resource.handleResponse {
            diplomaApi.addFeedback(
                AddFeedbackBody(
                    content = content,
                    dateCreate = dateFeedback,
                    idHouse = idHouse,
                    idUser = idUser,
                    isBlocked = isBlocked,
                    rang = rang
                )
            )
        }
    }

    override suspend fun updateFeedback(
        feedback: Feedback
    ) {
        Resource.handleResponse {
            diplomaApi.updateFeedback(
                UpdateFeedbackBody(
                    id = feedback.id,
                    content = feedback.content,
                    dateCreate = feedback.dateFeedback,
                    idHouse = feedback.idHouse,
                    idUser = feedback.idUser,
                    isBlocked = feedback.isBlocked,
                    rang = feedback.rang
                )
            )
        }
    }

    override suspend fun getAllNews(): Resource<List<Note>> {
        return Resource.handleResponse {
            diplomaApi.getAllNews().map { it.mapFromDtoToNote() }
        }
    }

    override suspend fun addNewNews(
        title: String,
        content: String,
        dateCreate: String
    ) {
        Resource.handleResponse {
            diplomaApi.addNews(
                AddNewsBody(
                    title = title,
                    content = content,
                    dateCreate = dateCreate
                )
            )
        }
    }

    override suspend fun updateNews(
        note: Note
    ) {
        Resource.handleResponse {
            diplomaApi.updateNews(
                UpdateNewsBody(
                    id = note.id,
                    content = note.content,
                    dateCreate = note.dateCreate,
                    title = note.title
                )
            )
        }
    }

    override suspend fun deleteNews(
        newsId: Int
    ) {
        Resource.handleResponse {
            diplomaApi.deleteNews(newsId)
        }
    }

    override suspend fun addNewCall(
        name: String,
        phone: String
    ) {
        Resource.handleResponse {
            diplomaApi.addCall(
                AddCallBody(
                    name = name,
                    phone = phone
                )
            )
        }
    }

    override suspend fun getAllCalls(): Resource<List<Call>> {
        return Resource.handleResponse {
            diplomaApi.getAllCall().map { it.mapFromDtoToCall() }
        }
    }

    override suspend fun getAllHouses(): Resource<List<House>> {
        return Resource.handleResponse {
            diplomaApi.getAllHouses().map { it.mapFromDtoToHouse() }
        }
    }

    override suspend fun getHouseById(idHouse: Int): Resource<HouseDetail> {
        return Resource.handleResponse {
            diplomaApi.getHouseById(idHouse).mapFromDtoToHouseDetail()
        }
    }

}

