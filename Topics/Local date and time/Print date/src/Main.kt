import kotlinx.datetime.*

fun printDate(date: String) {
    // Write your code here
    val localDate = LocalDate.parse(date)
    println("${localDate.dayOfWeek}, ${localDate.month} ${localDate.dayOfMonth}, ${localDate.year}")
}

fun main() {
    val date = readln()
    printDate(date)
}