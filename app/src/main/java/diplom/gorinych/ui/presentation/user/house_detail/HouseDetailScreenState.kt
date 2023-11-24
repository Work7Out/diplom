package diplom.gorinych.ui.presentation.user.house_detail

import diplom.gorinych.domain.model.HouseDetail

data class HouseDetailScreenState (
    val message:String? = null,
    val house: HouseDetail? = null,
    val idUser: Int = -1,
    val nameUser: String = ""
)