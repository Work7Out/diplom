package diplom.gorinych.domain.model

data class Feedback(
    val id: Int,
    val idUser: Int,
    val name: String,
    val idHouse: Int,
    val isBlocked: Boolean,
    val content: String,
    val rang: Int,
    val dateFeedback: String
)
