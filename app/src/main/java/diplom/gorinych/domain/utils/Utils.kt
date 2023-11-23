package diplom.gorinych.domain.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDate.formatLocalDateRu(): String {
    val formatter = DateTimeFormatter.ofPattern(FORMAT_YYYY_MM_DD, Locale(LOCAL_RU))
    return this.format(formatter)
}