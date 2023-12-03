package diplom.gorinych.domain.utils

import diplom.gorinych.domain.model.Reserve
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


fun LocalDate.formatLocalDateRu(): String {
    val formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD, Locale(LOCAL_RU))
    return this.format(formatter)
}

fun convertStringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD, Locale(LOCAL_RU))
    return LocalDate.parse(dateString, formatter)
}


fun calculateComfirmOrders(reserves: List<Reserve>): Int {
    return reserves.count { it.confirmReservation == ACTIVE }
}

fun calculateAllSum(reserves: List<Reserve>): Double {
    return reserves.sumOf { it.amount }
}

fun calculateMonthSum(reserves: List<Reserve>): Double {
    return reserves
        .filter { convertStringToDate(it.dateCreate) >= LocalDate.now().minusMonths(1) }
        .sumOf { it.amount }
}

fun calculateSeasonSum(reserves: List<Reserve>): Double {
    return reserves
        .filter { convertStringToDate(it.dateCreate) >= LocalDate.now().minusYears(1) }
        .sumOf { it.amount }
}

fun isEmailValid(email: String): Boolean {
    val emailPattern =
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    return email.matches(emailPattern.toRegex())
}

fun sendMail(
    login: String,
    password: String,
    email:String,
    theme: String,
    content: String
) {
    val props = Properties()
    props["mail.smtp.host"] = "smtp.gmail.com"
    props["mail.smtp.socketFactory.port"] = "465"
    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.port"] = "465"

    val session = Session.getInstance(props, object : javax.mail.Authenticator() {
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