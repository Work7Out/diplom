package diplom.gorinych.data.remote

import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface DyplomaApi {

    @POST("users/login")
    suspend fun login (@Body loginBody: LoginBody) : UserDto?
}