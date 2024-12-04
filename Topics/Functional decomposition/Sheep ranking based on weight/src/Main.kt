// The function calculateRank is your task.
// It takes a parameter, the weight of the sheep, which is an integer.
// It should return the rank of the sheep, which is also an integer.
fun calculateRank(weight: Int): Int {
    // Write your code here.
    return when(weight) {
        in 1 until 30 -> 1
        in 30 until 50 -> 2
        in 50 until 70 -> 3
        else -> 4
    }
}

// The main function reads the input and calls calculateRank to rank the sheep.
fun main() {
    // Read the weight of the sheep
    val weight = readln().toInt()

    // Call calculateRank and print the result
    println(calculateRank(weight))
}