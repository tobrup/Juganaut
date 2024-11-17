package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.ui.swing.MenuGui


fun main(args: Array<String>) {


    val playerX = args[0].toInt()
    val playerY = args[1].toInt()
    val diamondsInGame = args[2].toInt()
    val menuGui = MenuGui(playerX, playerY, diamondsInGame)
    menuGui.isVisible = true

}

