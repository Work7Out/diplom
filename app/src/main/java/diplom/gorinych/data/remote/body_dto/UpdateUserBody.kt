package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class UpdateUserBody(
    @SerializedName("e_mail")
    val eMail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_blocked")
    val isBlocked: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String
)