package diplom.gorinych.data.remote.body_dto

import com.google.gson.annotations.SerializedName

data class ConfirmRegistrationBody(
    @SerializedName("confirm_reservation")
    val statusReserve: String
)