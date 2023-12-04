package diplom.gorinych.domain.model

data class Promo(
    val id: Int,
    val valueDiscount: Int,
    val description: String,
    val isActive: Boolean,
)
