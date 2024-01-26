package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class AddAddonBody(
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
)