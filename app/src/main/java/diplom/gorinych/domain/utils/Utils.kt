package diplom.gorinych.domain.utils

import diplom.gorinych.domain.model.Reserve
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


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