package diplom.gorinych.data.remote.body_dto

import com.google.gson.annotations.SerializedName

data class AddHistoryBody (
    @SerializedName("additions")
    val additions: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("confirm_reservation")
    val confirmReservation: String,
    @SerializedName("data_begin")
    val dataBegin: String,
    @SerializedName("data_end")
    val dataEnd: String,
    @SerializedName("date_create")
    val dateCreate: String,
    @SerializedName("id_house")
    val idHouse: Int,
    @SerializedName("id_user")
    val idUser: Int
)