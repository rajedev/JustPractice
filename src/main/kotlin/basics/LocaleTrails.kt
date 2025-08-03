package basics

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rajendhiran Easu on 03/08/25.
 * Description: This code demonstrates how the `SimpleDateFormat`
 * class behaves differently based on the device's locale settings.
 * It shows that the date parsing can yield different results depending on the locale,
 * even when using the same date format string.
 */

const val HTTP_STANDARD_RESPONSE_LAST_MODIFIED_DATE = "EEE, dd MMM yyyy HH:mm:ss z"

fun main() {
    val date = "Wed, 30 Jul 2025 19:18:22 GMT"

    // English Device - Works ✅
    verifyWithLocale(deviceLocale = Locale.US, inputDate = date)

    // French Device - Fails ❌
    // Expects: "mer., 30 juil. 2025 19:18:22 GMT" ❌
    // But gets: "Wed, 30 Jul 2025 19:18:22 GMT"
    verifyWithLocale(deviceLocale = Locale.FRANCE, inputDate = date)//, parseLocale = Locale.US)

    // German Device - Fails ❌
    // Expects: "Mi., 30 Juli 2025 19:18:22 GMT" ❌
    // But gets: "Wed, 30 Jul 2025 19:18:22 GMT"
    verifyWithLocale(deviceLocale = Locale.GERMANY, inputDate = date)//, parseLocale = Locale.US)

    // UK Device-Works ✅
    verifyWithLocale(deviceLocale = Locale.UK, inputDate = date)

}

private fun verifyWithLocale(deviceLocale: Locale, inputDate: String, parseLocale: Locale? = null) {
    try {
        Locale.setDefault(deviceLocale)
        val sdf = SimpleDateFormat(HTTP_STANDARD_RESPONSE_LAST_MODIFIED_DATE, parseLocale ?: Locale.getDefault())
        val calendar = Calendar.getInstance(sdf.timeZone)
        calendar.time = sdf.parse(inputDate)
        println(" ${Locale.getDefault()} = ${calendar.time}")
    } catch (e: Exception) {
        // e.printStackTrace()
        println("Error parsing date: ${e.message} - ${Locale.getDefault()}")
    }
}