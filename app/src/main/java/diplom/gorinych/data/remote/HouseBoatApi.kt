package diplom.gorinych.data.remote

import diplom.gorinych.data.remote.body_dto.AddAddonBody
import diplom.gorinych.data.remote.body_dto.AddCallBody
import diplom.gorinych.data.remote.body_dto.AddFeedbackBody
import diplom.gorinych.data.remote.body_dto.AddHistoryBody
import diplom.gorinych.data.remote.body_dto.AddNewsBody
import diplom.gorinych.data.remote.body_dto.AddPromoBody
import diplom.gorinych.data.remote.body_dto.LoginBody
import diplom.gorinych.data.remote.body_dto.RegistrationBody
import diplom.gorinych.data.remote.body_dto.UpdateAddonBody
import diplom.gorinych.data.remote.body_dto.UpdateCallBody
import diplom.gorinych.data.remote.body_dto.UpdateFeedbackBody
import diplom.gorinych.data.remote.body_dto.UpdateHistoryBody
import diplom.gorinych.data.remote.body_dto.UpdateNewsBody
import diplom.gorinych.data.remote.body_dto.UpdatePromoBody
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
    suspend fun getUserById(@Path("userId") userId: Int): UserDto

    @PATCH("users")
    suspend fun updateUser(@Body updateUserBody: UpdateUserBody)

    @POST("users")
    suspend fun registration(@Body registrationBody: RegistrationBody): UserDto?

    //Reserve
    @POST("history")
    suspend fun addNewHistory(@Body addHistoryBody: AddHistoryBody)

    @GET("history/status")
    suspend fun getHistoryByStatus(@Query("status") status: String): List<HistoryDto>

    @GET("history")
    suspend fun getAllHistory(): List<HistoryDto>

    @GET("history/user")
    suspend fun getHistoryByUser(@Query("userId") userId: Int): List<HistoryDto>

    @GET("history/house")
    suspend fun getHistoryByHouse(@Query("houseId") houseId: Int): List<HistoryDto>

    @PATCH("history")
    suspend fun updateHistory(@Body updateHistoryBody: UpdateHistoryBody)

    @DELETE("history/{id}")
    suspend fun deleteHistory(@Path("id") idHistory: Int)


    //Addons
    @POST("addons")
    suspend fun addAddon(@Body addAddonBody: AddAddonBody)

    @PATCH("addons")
    suspend fun updateAddon(@Body updateAddonBody: UpdateAddonBody)

    @DELETE("addons/{addonId}")
    suspend fun deleteAddon(@Path("addonId") addonId: Int)

    @GET("addons")
    suspend fun getAllAddons(): List<AddonDto>


    //Promos
    @POST("promos")
    suspend fun addPromo(@Body addPromoBody: AddPromoBody)

    @PATCH("promos")
    suspend fun updatePromo(@Body updatePromoBody: UpdatePromoBody)

    @GET("promos")
    suspend fun getAllPromos(): List<PromoDto>

    @GET("promos/search")
    suspend fun getPromosByDescription(@Query("description") query: String): PromoDto?


    //Feedbacks
    @POST("feedback")
    suspend fun addFeedback(@Body addFeedbackBody: AddFeedbackBody)

    @GET("feedback")
    suspend fun getAllFeedbacks(): List<FeedbackDto>

    @GET("feedback/house")
    suspend fun getFeedbacksByHouse(@Query("houseId") houseId: Int): List<FeedbackDto>

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

    @PATCH("calls")
    suspend fun updateCall(@Body updateCallBody: UpdateCallBody)

    @GET("calls")
    suspend fun getAllCall(): List<CallDto>

    //House bots
    @GET("houses")
    suspend fun getAllHouses(): List<HouseDto>

    @GET("houses/{houseId}")
    suspend fun getHouseById(@Path("houseId") houseId: Int): HouseDto
}