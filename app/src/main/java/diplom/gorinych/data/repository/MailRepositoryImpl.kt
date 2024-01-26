package diplom.gorinych.data.repository

import diplom.gorinych.domain.repository.MailRepository
import java.util.Properties
import javax.inject.Inject
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailRepositoryImpl @Inject constructor(
    private val properties: Properties
): MailRepository {

    override suspend fun sendEmail(
        login: String,
        password: String,
        email:String,
        theme: String,
        content: String
    ) {
        val session = Session.getInstance(properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(login, password)
            }
        })

        try {
            val message: Message = MimeMessage(session).apply {
                setFrom(InternetAddress(login))
                setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
                )
                subject = theme
                setText(content)
            }

            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}