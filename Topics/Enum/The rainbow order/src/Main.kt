fun main() {
    val color = readln()
    println(Color.valueOf(color.uppercase()).ordinal + 1 )
}

enum class Color {
    RED, ORANGE,YELLOW, GREEN, BLUE, INDIGO, VIOLET
}