package diplom.gorinych.domain.model


data class Reserve(
    val id: Int,
    val idUser: Int,
    val idHouse: Int,
    val dateBegin: String,
    val dateEnd: String,
    val confirmReservation: String,
    val amount: Double,
    val additions: String,
    val dateCreate: String
)
