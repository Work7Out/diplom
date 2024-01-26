package diplom.gorinych.data.remote.body_dto

import com.google.gson.annotations.SerializedName

data class UpdateAddonBody (
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
)