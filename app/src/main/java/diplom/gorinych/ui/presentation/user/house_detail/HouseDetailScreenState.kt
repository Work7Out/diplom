package diplom.gorinych.ui.presentation.user.house_detail

import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Promo
import diplom.gorinych.domain.model.Reserve

data class HouseDetailScreenState (
    val message:String? = null,
    val house: HouseDetail? = null,
    val promo: Promo? = null,
    val reserves: List<Reserve> = emptyList(),
    val feedbacks: List<Feedback> = emptyList(),
    val additions: List<Addon> = emptyList(),
    val additionsSelected: List<Addon> = emptyList(),
    val idUser: Int = -1,
    val nameUser: String = "",
    val idHouse: Int = -1,
    val isLoading: Boolean = true,
    val amountReserve: Double = 0.0,
    val isEnableFeedback: Boolean = false,
) {
    val sumAddons = additionsSelected.sumOf { it.price }
}