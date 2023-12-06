package diplom.gorinych.domain.repository

interface MailRepository {
    suspend fun sendEmail(login: String, password: String, email: String, theme: String, content: String)
}