package diplom.gorinych.ui.presentation.user.house_detail

import diplom.gorinych.domain.model.Addon
import java.time.LocalDate

sealed class HouseDetailEvent {
    class AddReserve(
        val dateBegin: LocalDate,
        val dateEnd: LocalDate,
        val valueDays: Int,
        val addons: List<String>,
      //  val additions: String
    ): HouseDetailEvent()

    class AddFeedback(
        val rang:Int,
        val content:String
    ):HouseDetailEvent()

    class AddAddon(val addon: Addon):HouseDetailEvent()

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