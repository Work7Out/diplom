package diplom.gorinych.domain.repository

interface SharedRepository {
    fun getUser(): Int?
    fun setUser(date: Int)
    fun getRole(): String?
    fun setRole(date: String)
}