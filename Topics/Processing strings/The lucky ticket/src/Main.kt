fun main() {
    // write your code here
    val input = readln()
    val charList = input.toCharArray()
    var sumFirst: Int = 0
    var sumSecond: Int = 0

    for(i in 0..2) {
        sumFirst += charList[i].digitToInt()
    }

    for(i in 3..5) {
        sumSecond += charList[i].digitToInt()
    }

    println(if(sumFirst == sumSecond) "Lucky" else "Regular")
}