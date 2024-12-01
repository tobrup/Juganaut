package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.ui.swing.MainGui


fun main(args: Array<String>) {
    val providedArgs = checkArgs(args)
    val playerX = providedArgs[0].toInt()
    val playerY = providedArgs[1].toInt()
    val diamondsInGame = providedArgs[2].toInt()
    checkArgs(args)
    MainGui().showMenu(playerX, playerY, diamondsInGame)

}

/**
 * Checkt, ob die Argumente alle vorhanden sind. Wenn nicht, dann gibt er die Standartwerte aus.
 * Diese sind: playerX = 10; playerY = 10; diamondsInGame = 3;
 */
private fun checkArgs(args: Array<String>): Array<String> {
    if (args.size == 3) {
        return args
    }
    return arrayOf("10", "10", "3")
}

