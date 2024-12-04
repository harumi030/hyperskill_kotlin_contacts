fun main() {
    val text = readln()
    // write your code here
    val regex = "[aA]+".toRegex()
    println(text.replace(regex, "a"))
}