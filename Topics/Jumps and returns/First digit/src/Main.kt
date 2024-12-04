fun main() {
    // put your code here
    val str = readln()
    for(l in str) {
        if(l.isDigit()) {
            println(l)
            break
        }
    }
}