package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class AddNewsBody(
    @SerializedName("content")
    val content: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("title")
    val title: String
)