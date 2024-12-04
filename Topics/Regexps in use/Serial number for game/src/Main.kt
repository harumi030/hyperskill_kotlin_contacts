fun findSerialNumberForGame(listGames: List<String>) {
    val gameName = readln()
    val regex = """$gameName@(?<xbox>\d+)@(?<pc>\d+)""".toRegex()
    for(game in listGames) {
        if(game.startsWith(gameName)) {
            val match = regex.find(game)!!
            println("Code for Xbox - ${match.groups["xbox"]?.value}, for PC - ${match.groups["pc"]?.value}")
            break
        }
    }
}