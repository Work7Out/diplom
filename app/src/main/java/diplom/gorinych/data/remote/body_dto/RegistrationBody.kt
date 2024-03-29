package diplom.gorinych.data.remote.body_dto


import com.google.gson.annotations.SerializedName

data class RegistrationBody(
    @SerializedName("e_mail")
    val eMail: String,
    @SerializedName("is_blocked")
    val isBlocked: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String
)