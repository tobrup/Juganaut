package de.glueckstobi.juganaut.ui.swing.game

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.logic.PlayerActions
import de.glueckstobi.juganaut.bl.logic.PlayerInput
import de.glueckstobi.juganaut.bl.logic.PlayerMovement
import de.glueckstobi.juganaut.bl.space.Action
import de.glueckstobi.juganaut.bl.space.Direction
import java.awt.event.KeyEvent

/**
 * Verwaltet die Benutzer-Eingaben und leitet sie an die Spiel-Logik weiter.
 */
class UserInputHandler(val game: Game) {

    /**
     * Wird aufgerufen, wenn eine Taste gedrückt wird.
     * @param e Enthält Informationen über die Taste
     */
    fun onKeyPress(e: KeyEvent) {
        val input = getPlayerInput(e)
        input?.let {
            game.turnController.playerController.playerInputPressed(input)
        }
    }

    /**
     * Wird aufgerufen, wenn eine Taste losgelassen wird.
     * @param e Enthält Informationen über die Taste
     */
    fun onKeyRelease(e: KeyEvent) {
        game.turnController.playerController.playerInputReleased()
    }

    /**
     * Gibt das richtige Spieler-Kommando für die Taste zurück.
     * Wenn es kein entsprechendes Spieler-Kommando gibt, wird null zurückgegeben.
     * @param e Enthält Informationen über die Taste
     */
    private fun getPlayerInput(e: KeyEvent): PlayerInput? {
        if (getDirection(e) == null) {
            return getAction(e)?.let { PlayerActions(it) }
        }
        return getDirection(e)?.let { PlayerMovement(it) }
    }

    /**
     * Gibt die Richtung zurück, in die der Spieler laufen will,
     * wenn er die angegebene Taste drückt.
     * Wenn es keine Bewegungs-Taste ist, wird null zurückgegeben.
     * @param e Enthält Informationen über die Taste
     */
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

    private fun getAction(e: KeyEvent): Action? {
        return when (e.keyCode) {
            KeyEvent.VK_Q -> Action.Quit
            KeyEvent.VK_R -> Action.Restart
            else -> null
        }
    }
}