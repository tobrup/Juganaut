package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.logic.GameOverReason
import de.glueckstobi.juganaut.bl.logic.TurnController
import kotlin.system.exitProcess

/**
 * Beschreibt das Spiel mit allen Daten und der Spiel-Logik.
 * @param world die Spielwelt mit allen Feldern.
 * @param diamondsInGame Die Anzahl der gesamten Diamanten in einem Spiel
 */
class Game(val world: World, val diamondsInGame : Int) {

    /**
     * Wenn der Spieler verloren hat, wird hier der Grund für das GameOver gespeichert
     */
    var gameOverReason: GameOverReason? = null

    /**
     * Hier wird die Anzahl der gesammelten Diamanten gespeichert
     */
    var diamondCount : Int = 0
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
        exitProcess(0)
    }

    fun restart() {
        TODO("Not yet implemented")
    }
}