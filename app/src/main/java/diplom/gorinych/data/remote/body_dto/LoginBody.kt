package diplom.gorinych.data.remote.body_dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginBody(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String,
): Serializable
