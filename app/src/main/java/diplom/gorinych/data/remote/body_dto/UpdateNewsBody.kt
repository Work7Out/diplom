package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class UpdateNewsBody(
    @SerializedName("content")
    val content: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)