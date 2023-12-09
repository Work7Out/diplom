package diplom.gorinych.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FeedbackDto(
    @SerializedName("content")
    val content: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_house")
    val idHouse: Int,
    @SerializedName("id_user")
    val idUser: Int,
    @SerializedName("is_blocked")
    val isBlocked: Boolean,
    @SerializedName("rang")
    val rang: Int
)