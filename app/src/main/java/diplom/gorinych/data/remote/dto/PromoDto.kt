package diplom.gorinych.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PromoDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("value_discount")
    val valueDiscount: Int
)