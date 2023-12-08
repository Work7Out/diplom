package diplom.gorinych.data.repository

import diplom.gorinych.data.mapper.mapDtoToUser
import diplom.gorinych.data.remote.DyplomaApi
import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.domain.model.User
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.Resource
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val diplomaApi: DyplomaApi
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
}