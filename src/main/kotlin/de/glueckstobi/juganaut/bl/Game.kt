package de.glueckstobi.juganaut.bl

import com.adonax.audiocue.AudioCue
import de.glueckstobi.juganaut.bl.logic.GameOverReason
import de.glueckstobi.juganaut.bl.logic.TurnController
import de.glueckstobi.juganaut.bl.logic.WinningReason
import de.glueckstobi.juganaut.ui.swing.MainGui
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
     * Hier wird gespeichert, ob der Spieler gewonnen hat
     */
    var winningReason: WinningReason? = null
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
        MainGui.sfxAudioCue = AudioCue.makeStereoCue(this.javaClass.getResource("/sound/lose_game1.wav"), 4)
        MainGui.sfxAudioCue.open()
        MainGui.sfxAudioCue.play()
        if (MainGui.musicAudioCue.getIsActive(MainGui.musicAudioCue.obtainInstance())) {
            MainGui.musicAudioCue.close()
        }
        gameOverReason = reason
    }

    /**
     * Wird aufgerufen, wenn der Spieler gewonnen hat.
     * @param reason der Grund für den Gewinn
     */
    fun win(reason: WinningReason) {
        MainGui.sfxAudioCue = AudioCue.makeStereoCue(this.javaClass.getResource("/sound/win_game.wav"), 4)
        MainGui.sfxAudioCue.open()
        MainGui.sfxAudioCue.play()
        if (MainGui.musicAudioCue.getIsActive(MainGui.musicAudioCue.obtainInstance())) {
            MainGui.musicAudioCue.close()
        }
        winningReason = reason

    }

    /**
     * Das Spiel wird Beendet
     */
    fun quit() {
        exitProcess(0)
    }

    /**
     * Das Spiel wird neu gestartet
     */
    fun restart() {
        TODO("Not yet implemented")
    }
}