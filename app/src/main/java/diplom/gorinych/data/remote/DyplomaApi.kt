package diplom.gorinych.data.remote

import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
import diplom.gorinych.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DyplomaApi {

    @POST("users/login")
    suspend fun login (@Body loginBody: LoginBody) : UserDto?

    @GET("users")
    suspend fun getAllUsers () : List<UserDto>?

    @POST("users")
    suspend fun registration (@Body registrationBody: RegistrationBody) : UserDto?
}