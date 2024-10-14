package de.glueckstobi.juganaut.ui.swing

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.ui.swing.game.UserInputHandler
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants


/**
 * Enthält die gesamte Benutzer-Oberfläche.
 */
class MainGui {

    companion object {
        /**
         * Die Zeit einer Spiel-Runde (in Millisekunden)
         */
        val tickDurationMs = 500.toLong()
    }

    /**
     * Die Uhr, die regelmäßig eine Spiel-Runde ausführt und das Ergebnis anzeigt.
     */
    inner class RenderCycle(val game: Game): Runnable {

        /**
         * True, wenn die Spiel-Uhr nicht weiterlaufen soll (weil das Spiel beendet ist)
         */
        @Volatile
        var clockStopped = false

        /**
         * Wird regelmäßig für jede neue Spiel-Runde aufgerufen.
         */
        override fun run() {
            var previousTickMs = nanoToMilli(System.nanoTime())
            while (!clockStopped && !Thread.currentThread().isInterrupted) {
                val now = nanoToMilli(System.nanoTime())
                val nextTick = previousTickMs + tickDurationMs
                val sleepTime = nextTick - now
                Thread.sleep(sleepTime)
                previousTickMs = nextTick
                SwingUtilities.invokeLater {
                    tickRenderCycle(game)
                }
            }
        }

    }

    /**
     * Das Fenster
     */
    private val window: JFrame = JFrame("Juganaut")

    /**
     * Hört auf Tasten-Drücke und gibt es an den [inputController] weiter.
     */
    private val keyListener = object : KeyAdapter() {
        override fun keyPressed(e: KeyEvent) {
            inputController?.onKeyPress(e)
        }

        override fun keyReleased(e: KeyEvent) {
            inputController?.onKeyRelease(e)
        }
    }

    private var inputController: UserInputHandler? = null

    private var renderCycle: RenderCycle? = null

    /**
     * Startet das Spiel.
     */
    fun startPlaying(game: Game) {
        val renderer = WorldRenderer(game.world)
        inputController = UserInputHandler(game)
        showWindow(renderer)
        startRenderCycle(game)
    }

    /**
     * Startet den die Uhr, die regelmäßig eine Spiel-Runde ausführt und den aktuellen Spiel-Zustand anzeigt.
     */
    private fun startRenderCycle(game: Game) {
        renderCycle = RenderCycle(game)
        Thread(renderCycle, "TickThread").start()
    }

    /**
     * Wird für jede Spiel-Runde aufgerufen.
     * Führt die Spiel-Logik aus und zeichnet danach das Ergebnis neu.
     */
    private fun tickRenderCycle(game: Game) {
        game.turnController.tick()
        window.repaint()
    }


    /**
     * Baut das Fenster auf und zeigt es an.
     */
    private fun showWindow(renderer: WorldRenderer) {
        window.contentPane = renderer
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        window.setSize(1024, 800)

        window.setVisible(true)

        window.contentPane.requestFocus()
        window.contentPane.addKeyListener(keyListener)

        window.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                renderCycle?.clockStopped = true
                window.dispose()
            }
        })
    }

    /**
     * Umrechnung von Nanosekunden in Millisekunden.
     */
    private fun nanoToMilli(nano: Long): Long = nano / 1_000_000

}