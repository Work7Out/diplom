package diplom.gorinych.ui.presentation.user.house_detail

import java.time.LocalDate

sealed class HouseDetailEvent {
    class AddReserve(
        val dateBegin: LocalDate,
        val dateEnd: LocalDate,
        val valueDays: Int,
    ): HouseDetailEvent()

  /*  class DeleteReserve(
        val id: Int,
        val idUser: Int,
        val idHouse: Int,
        val dateBegin: String,
        val dateEnd: String,
        val confirmReservation: String,
        val amount: Double,
        val dateCreate: String
    ): HouseDetailEvent()*/
}