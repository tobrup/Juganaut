package de.glueckstobi.juganaut.ui.swing

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.ui.swing.game.UserInputController
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.WindowConstants




class MainGui {

    private val window: JFrame = JFrame("Juganaut")

    fun startPlaying(game: Game) {
        val renderer = WorldRenderer(game.world)
        window.contentPane = renderer
        showWindow()
        initializeUserInputController(game)
    }

    private fun initializeUserInputController(game: Game) {
        val inputController = UserInputController(game)
        window.contentPane.requestFocus()
        window.contentPane.addKeyListener(object: KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                inputController.onKeyPress(e)
                window.repaint()
            }

            override fun keyReleased(e: KeyEvent) {
                inputController.onKeyRelease(e)
            }
        })
    }

    private fun showWindow() {
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        window.setSize(1024, 800)

        window.setVisible(true)
        window.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                window.dispose()
            }
        })

    }

}