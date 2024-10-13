package de.glueckstobi.juganaut.ui.swing

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.ui.swing.game.UserInputController
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants




class MainGui {

    companion object {
        val tickDurationMs = 500.toLong()
    }

    private val window: JFrame = JFrame("Juganaut")

    @Volatile
    private var clockStopped = false

    fun startPlaying(game: Game) {
        val renderer = WorldRenderer(game.world)
        window.contentPane = renderer
        showWindow()
        initializeUserInputController(game)
        startRenderCycle(game)
    }

    private fun initializeUserInputController(game: Game) {
        val inputController = UserInputController(game)
        window.contentPane.requestFocus()
        window.contentPane.addKeyListener(object: KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                inputController.onKeyPress(e)
            }

            override fun keyReleased(e: KeyEvent) {
                inputController.onKeyRelease(e)
            }
        })
    }

    private fun startRenderCycle(game: Game) {
        Thread( {
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
        }, "TickThread").start()
    }

    private fun tickRenderCycle(game: Game) {
        game.turnController.tick()
        window.repaint()
    }


    private fun showWindow() {
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        window.setSize(1024, 800)

        window.setVisible(true)
        window.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                clockStopped = true
                window.dispose()
            }
        })
    }

    private fun nanoToMilli(nano: Long): Long = nano / 1_000_000

}