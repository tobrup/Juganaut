package de.glueckstobi.juganaut.ui.swing.game

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.logic.Pause
import de.glueckstobi.juganaut.bl.logic.PlayerInput
import de.glueckstobi.juganaut.bl.logic.PlayerMovement
import de.glueckstobi.juganaut.bl.space.Direction
import java.awt.event.KeyEvent

class UserInputController(val game: Game) {

    fun onKeyPress(e: KeyEvent) {
        val input = getPlayerInput(e)
        input?.let {
            game.turnController.setPlayerInput(input)
        }
    }

    fun onKeyRelease(e: KeyEvent) {
        // do nothing
    }

    private fun getPlayerInput(e: KeyEvent): PlayerInput? {
        return getMoveInput(e) ?: getSpecialInput(e)
    }

    private fun getMoveInput(e: KeyEvent): PlayerMovement? {
        return getDirection(e)?.let { PlayerMovement(it) }
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

    private fun getSpecialInput(e: KeyEvent): PlayerInput? {
        return when (e.keyCode) {
            KeyEvent.VK_SPACE -> Pause
            else -> null
        }
    }
}