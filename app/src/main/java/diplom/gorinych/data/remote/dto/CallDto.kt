package diplom.gorinych.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CallDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("is_cancelled")
    val isResponse: Boolean
)