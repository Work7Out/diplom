package diplom.gorinych.data.remote.dto


import com.google.gson.annotations.SerializedName

data class HouseDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("path_image")
    val pathImage: String,
    @SerializedName("price")
    val price: Double
)