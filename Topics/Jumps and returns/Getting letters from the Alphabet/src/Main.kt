fun main() {
    // put your code here
    val ch = readln().first()

    for(char in 'a'..'z') {
        if(char == ch) break
        print(char)
    }

}