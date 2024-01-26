package diplom.gorinych.ui.presentation.user.house_detail

import diplom.gorinych.domain.model.Addon
import java.time.LocalDate

sealed class HouseDetailEvent {
    class AddReserve(
        val dateBegin: LocalDate,
        val dateEnd: LocalDate,
        val amount: Double,
    ): HouseDetailEvent()

    class AddFeedback(
        val rang:Int,
        val content:String
    ):HouseDetailEvent()

    class AddAddon(val addon: Addon):HouseDetailEvent()
    class CheckPromo(val query: String):HouseDetailEvent()

}