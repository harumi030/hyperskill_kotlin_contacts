fun main() {
    // write your code here
    val num = readln().toInt()
    val numbers = IntArray(num) {readln().toInt()}
    val searchNum = readln().toInt()

    println(if(numbers.contains(searchNum)) "YES" else "NO")
}