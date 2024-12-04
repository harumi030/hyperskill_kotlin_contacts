import kotlinx.datetime.*


fun nextMonth(date: String): String {
    // Write your code here
    // Parse the date string into an Instant
    val parsedDate = Instant.parse(date)

    val period: DateTimePeriod = DateTimePeriod(months = 1)

    val nextMonth = parsedDate.plus(period, TimeZone.UTC)

    // Return the result as an ISO 8601 date-time string with UTC time
    return nextMonth.toString()
}

fun main() {
    val date = readln()
    println(nextMonth(date))
}