package diplom.gorinych.data.repository

import diplom.gorinych.data.mapper.mapDtoToUser
import diplom.gorinych.data.remote.HouseBoatApi
import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
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
}