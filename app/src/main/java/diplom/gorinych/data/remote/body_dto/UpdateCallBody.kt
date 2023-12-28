package diplom.gorinych.data.remote.body_dto

import com.google.gson.annotations.SerializedName

data class UpdateCallBody(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("is_response")
    val isResponse: Boolean
)