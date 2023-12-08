package diplom.gorinych.data.repository

import diplom.gorinych.data.mapper.mapDtoToUser
import diplom.gorinych.data.mapper.mapToReserve
import diplom.gorinych.data.remote.HouseBoatApi
import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
import diplom.gorinych.data.remote.body_dto.UpdateUserBody
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
            )?.mapDtoToUser()
        }
    }

    override suspend fun getAllUsers(): Resource<List<User>> {
        return Resource.handleResponse {
            diplomaApi.getAllUsers().map { it.mapDtoToUser() }
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
            ).map { it.mapToReserve() }
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
}