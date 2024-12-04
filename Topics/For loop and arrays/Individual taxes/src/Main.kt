fun main() {
    // write your code here
    val numCompanies = readln().toInt()
    val incomes = IntArray(numCompanies) { readln().toInt() }
    val taxRates = IntArray(numCompanies) {readln().toInt()}
    var maxTax:Double = 0.0
    var maxTaxIndex: Int = 0

    for (i in 0 until numCompanies) {
        var tax: Double = try {
            (incomes[i].toDouble() * taxRates[i].toDouble() / 100)
        } catch (e: Exception) {
            0.0
        }
        if(tax > maxTax) {
            maxTax = tax
            maxTaxIndex = i + 1
        }
    }

    println(maxTaxIndex)
}