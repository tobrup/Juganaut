package de.glueckstobi.juganaut.ui.swing.game

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Direction
import java.awt.event.KeyEvent

class UserInputController(val game: Game) {

    fun onKeyPress(e: KeyEvent) {
        val direction = getDirection(e)
        direction?.let {
            movePlayer(direction)
        }
    }

    private fun getDirection(e: KeyEvent): Direction? {
        return when (e.keyCode) {
            KeyEvent.VK_UP -> Direction.Up
            KeyEvent.VK_DOWN -> Direction.Down
            KeyEvent.VK_LEFT -> Direction.Left
            KeyEvent.VK_RIGHT -> Direction.Right

            KeyEvent.VK_W -> Direction.Up
            KeyEvent.VK_A -> Direction.Left
            KeyEvent.VK_S -> Direction.Down
            KeyEvent.VK_D -> Direction.Right

            else -> null
        }
    }

    fun onKeyRelease(e: KeyEvent) {

    }

    private fun movePlayer(direction: Direction) {
        game.world.playerCoord = game.world.playerCoord.move(direction)
    }

}