package diplom.gorinych.data.remote

import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
import diplom.gorinych.data.remote.body_dto.UpdateUserBody
import diplom.gorinych.data.remote.body_dto.AddAddonBody
import diplom.gorinych.data.remote.body_dto.AddPromoBody
import diplom.gorinych.data.remote.dto.AddonDto
import diplom.gorinych.data.remote.dto.HistoryDto
import diplom.gorinych.data.remote.dto.PromoDto
import diplom.gorinych.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface HouseBoatApi {
    //Users
    @POST("users/login")
    suspend fun login (@Body loginBody: LoginBody) : UserDto?

    @GET("users")
    suspend fun getAllUsers () : List<UserDto>

    @PATCH("users")
    suspend fun updateUser (@Body updateUserBody: UpdateUserBody)

    @POST("users")
    suspend fun registration (@Body registrationBody: RegistrationBody) : UserDto?

    //Reserve
    @GET("history/status")
    suspend fun getHistoryByStatus (@Query("status") status:String) : List<HistoryDto>



    //Addons
    @POST("addons")
    suspend fun addAddon (@Body addAddonBody: AddAddonBody)

    @GET("addons")
    suspend fun getAllAddons () : List<AddonDto>



    //Promos
    @POST("promos")
    suspend fun addPromo (@Body addPromoBody: AddPromoBody)

    @GET("promos")
    suspend fun getAllPromos () : List<PromoDto>


}