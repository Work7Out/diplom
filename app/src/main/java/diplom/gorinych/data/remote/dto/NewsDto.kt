package diplom.gorinych.data.remote.dto


import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("content")
    val content: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)