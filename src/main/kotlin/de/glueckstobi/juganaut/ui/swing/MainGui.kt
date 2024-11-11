package de.glueckstobi.juganaut.ui.swing

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.ui.swing.game.UserInputHandler
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import de.glueckstobi.juganaut.ui.swing.game.itemrenderer.StaticImageRenderer.loadImage
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*


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
    inner class RenderCycle(val game: Game) : Runnable {

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

    private val statusLabel: JLabel = JLabel(" ")
    private val diamondCountLabel:JLabel = JLabel("")

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
        updateStatus(game)
    }

    private fun updateStatus(game: Game) {
        if (game.gameOverReason != null) {
            statusLabel.foreground = Color.RED
            statusLabel.text = "GAME OVER!! R:Restart Q:Quit"
        } else if (game.winningReason != null) {
            statusLabel.foreground = Color.GREEN
            statusLabel.text = "Gewonnen!!!"
        } else {
            statusLabel.foreground = Color.WHITE
            statusLabel.text = "Viel Spaß!"

        }
        diamondCountLabel.text = game.diamondCount.toString() + " / " + game.diamondsInGame.toString()

    }


    /**
     * Baut das Fenster auf und zeigt es an.
     */
    private fun showWindow(renderer: WorldRenderer) {
        val contentPane = JPanel(BorderLayout())
        val statusPane = JPanel(GridLayout())
        val backgroundColor = Color.BLACK
        contentPane.add(statusPane, BorderLayout.NORTH)
        statusPane.add(statusLabel, 0)
        statusPane.add(diamondCountLabel, 1)
        contentPane.add(renderer, BorderLayout.CENTER)
        statusLabel.font = Font("Sans-Serif", Font.PLAIN, 30)
        statusLabel.isOpaque = true
        statusLabel.background = backgroundColor

        diamondCountLabel.foreground = Color.CYAN
        diamondCountLabel.isOpaque = true
        diamondCountLabel.background = backgroundColor
        diamondCountLabel.horizontalAlignment = SwingConstants.RIGHT
        diamondCountLabel.icon = ImageIcon(loadImage("/diamond.png"))
        diamondCountLabel.font = Font("Sans-Serif", Font.PLAIN, 30)
        diamondCountLabel.setHorizontalTextPosition(SwingConstants.LEFT)

        window.contentPane = contentPane

        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        window.setSize(1024, 700)

        window.isVisible = true

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