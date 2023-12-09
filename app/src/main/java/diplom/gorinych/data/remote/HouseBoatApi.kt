package diplom.gorinych.data.remote

import diplom.gorinych.data.remote.body_dto.AddAddonBody
import diplom.gorinych.data.remote.body_dto.AddCallBody
import diplom.gorinych.data.remote.body_dto.AddFeedbackBody
import diplom.gorinych.data.remote.body_dto.AddNewsBody
import diplom.gorinych.data.remote.body_dto.AddPromoBody
import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
import diplom.gorinych.data.remote.body_dto.UpdateHistoryBody
import diplom.gorinych.data.remote.body_dto.UpdateFeedbackBody
import diplom.gorinych.data.remote.body_dto.UpdateNewsBody
import diplom.gorinych.data.remote.body_dto.UpdateUserBody
import diplom.gorinych.data.remote.dto.AddonDto
import diplom.gorinych.data.remote.dto.CallDto
import diplom.gorinych.data.remote.dto.FeedbackDto
import diplom.gorinych.data.remote.dto.HistoryDto
import diplom.gorinych.data.remote.dto.HouseDto
import diplom.gorinych.data.remote.dto.NewsDto
import diplom.gorinych.data.remote.dto.PromoDto
import diplom.gorinych.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HouseBoatApi {
    //Users
    @POST("users/login")
    suspend fun login (@Body loginBody: LoginBody) : UserDto?

    @GET("users")
    suspend fun getAllUsers () : List<UserDto>

    @GET("users/{userId}")
    suspend fun getUserById (@Path("userId") userId: Int) : UserDto

    @PATCH("users")
    suspend fun updateUser(@Body updateUserBody: UpdateUserBody)

    @POST("users")
    suspend fun registration(@Body registrationBody: RegistrationBody): UserDto?

    //Reserve
    @GET("history/status")
    suspend fun getHistoryByStatus(@Query("status") status: String): List<HistoryDto>

    @GET("history")
    suspend fun getAllHistory(): List<HistoryDto>

    @PATCH("history")
    suspend fun updateHistory(@Body updateHistoryBody: UpdateHistoryBody)


    //Addons
    @POST("addons")
    suspend fun addAddon(@Body addAddonBody: AddAddonBody)

    @GET("addons")
    suspend fun getAllAddons(): List<AddonDto>


    //Promos
    @POST("promos")
    suspend fun addPromo(@Body addPromoBody: AddPromoBody)

    @GET("promos")
    suspend fun getAllPromos(): List<PromoDto>


    //Feedbacks

    @POST("feedback")
    suspend fun addFeedback(@Body addFeedbackBody: AddFeedbackBody)

    @GET("feedback")
    suspend fun getAllFeedbacks(): List<FeedbackDto>

    @PATCH("feedback")
    suspend fun updateFeedback(@Body updateFeedbackBody: UpdateFeedbackBody)

    //News
    @POST("news")
    suspend fun addNews(@Body addNewsBody: AddNewsBody)

    @PATCH("news")
    suspend fun updateNews(@Body updateNewsBody: UpdateNewsBody)

    @DELETE("news/{newsId}")
    suspend fun deleteNews(@Path("newsId") newsId: Int)

    @GET("news")
    suspend fun getAllNews(): List<NewsDto>

    //Calls
    @POST("calls")
    suspend fun addCall(@Body callBody: AddCallBody)

    @GET("calls")
    suspend fun getAllCall(): List<CallDto>

    //House bots

    @GET("houses")
    suspend fun getAllHouses(): List<HouseDto>
}