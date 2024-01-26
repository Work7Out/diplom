package diplom.gorinych.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AddonDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
)