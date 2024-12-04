fun main() {
    // write your code here
    val url = readln()
    val params = url.substringAfter("?").split("&")
    var password = ""

    for(param in params) {
        if(param.substringAfter("=").isEmpty()) {
            println("${param.replace("=", " : ")}not found")
        } else {
            println(param.replace("=", " : "))
        }
        if(param.startsWith("pass")) {
            password = param.substringAfter("=")
        }
    }

    if(password.isNotEmpty()) {
        println("password : $password")
    }
}