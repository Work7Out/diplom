package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class AddFeedbackBody(
    @SerializedName("content")
    val content: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("id_house")
    val idHouse: Int,
    @SerializedName("id_user")
    val idUser: Int,
    @SerializedName("user_name")
    val name: String,
    @SerializedName("is_blocked")
    val isBlocked: Boolean,
    @SerializedName("rang")
    val rang: Int
)