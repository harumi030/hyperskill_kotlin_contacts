import kotlin.math.abs
import kotlin.math.hypot

fun perimeter(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, x4: Double = x1, y4: Double = y1): Double {
    val diffX1 = abs(x2 - x1)
    val diffY1 = abs(y2 - y1)
    val diffX2 = abs(x3 - x2)
    val diffY2 = abs(y3 - y2)
    val diffX3 = abs(x4 - x3)
    val diffY3 = abs(y4 - y3)
    val diffX4 = abs(x1 - x4)
    val diffY4 = abs(y4 - y1)

    return hypot(diffX1, diffY1) + hypot(diffX2, diffY2) + hypot(diffX3, diffY3) + hypot(diffX4, diffY4)
}