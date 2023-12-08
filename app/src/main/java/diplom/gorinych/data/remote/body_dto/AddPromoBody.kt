package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class AddPromoBody(
    @SerializedName("description")
    val description: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("value_discount")
    val valueDiscount: Int
)