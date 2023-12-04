package diplom.gorinych.ui.presentation.admin.addons

import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Promo

data class AddonScreenState(
    val addons: List<Addon> = emptyList(),
    val promos: List<Promo> = emptyList(),
    val message: String? = null,
    val idUser: Int = -1,
    val countNewReserves: Int = 0,
    val addonState: AddonState = AddonState.AdditionState
)
