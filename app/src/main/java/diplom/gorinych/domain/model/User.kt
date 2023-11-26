package diplom.gorinych.domain.model

data class User(
    val id: Int = 0,
    val name: String,
    val password: String,
    val role: String,
    val isBlocked: Boolean,
    val phone: String,
    val email: String,
)
