fun validatePassword(password: String): Boolean =
     if(password.length > 4) {
        println("Good password")
        true
    } else {
        println("Your password should be five or longer characters, please write a new password")
        false
    }




// do not change the code below
fun main() {
    var validationResult = false
    while (!validationResult) {
        val password = readln()
        validationResult = validatePassword(password)
    }
}