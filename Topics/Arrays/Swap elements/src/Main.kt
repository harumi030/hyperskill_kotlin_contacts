fun main() {    
    val numbers = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    // Do not touch lines above
    // Write only exchange actions here.
    val first = numbers.first()
    val last = numbers.last()
    numbers[0] = last
    numbers[numbers.size-1] = first
    // Do not touch lines below
    println(numbers.joinToString(separator = " "))
}