package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.logic.GameOverReason
import de.glueckstobi.juganaut.bl.logic.PlayerController
import de.glueckstobi.juganaut.bl.logic.TurnController
import de.glueckstobi.juganaut.ui.swing.MainGui

/**
 * Beschreibt das Spiel mit allen Daten und der Spiel-Logik.
 * @param world die Spielwelt mit allen Feldern.
 */
class Game(val world: World) {

    /**
     * Wenn der Spieler verloren hat, wird hier der Grund für das GameOver gespeichert
     */
    var gameOverReason: GameOverReason? = null

    /**
     * Enthält die Logik, um die Runden auszuführen.
     */
    val turnController = TurnController(this)

    /**
     * Wird aufgerufen, wenn der Spieler verloren hat.
     * @param reason der Grund für das GameOver
     */
    fun gameOver(reason: GameOverReason) {
        gameOverReason = reason
    }

    fun quit() {
        TODO("Not yet implemented")
    }

    fun restart() {
        TODO("Not yet implemented")
    }
}