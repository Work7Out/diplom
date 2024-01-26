package diplom.gorinych.data.remote.body_dto

import com.google.gson.annotations.SerializedName

data class PasswordChangeBody(
    @SerializedName("id")
    val id: Int,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("oldPassword")
    val oldPassword: String,
)
