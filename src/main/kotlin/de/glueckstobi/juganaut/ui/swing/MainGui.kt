package de.glueckstobi.juganaut.ui.swing

import com.adonax.audiocue.AudioCue
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.*
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
import kotlin.random.Random
import kotlin.random.nextInt


/**
 * Enthält die gesamte Benutzer-Oberfläche.
 */
class MainGui {

    companion object {
        /**
         * Die Zeit einer Spiel-Runde (in Millisekunden)
         */
        val tickDurationMs = 350.toLong()

        private var diamondSFX = Companion::class.java.getResource("/sound/collect_diamond.wav")

        private var mainMusic = Companion::class.java.getResource("/sound/main_loop.wav")

        var sfxAudioCue = AudioCue.makeStereoCue(diamondSFX, 4)

        var musicAudioCue = AudioCue.makeStereoCue(mainMusic, 4)

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
    fun startPlaying(playerX: Int, playerY: Int, diamondsInGame: Int): JFrame {
        val game = createGame(playerX, playerY, diamondsInGame)
        val renderer = WorldRenderer(game.world)
        inputController = UserInputHandler(game)
        startRenderCycle(game)
        sfxAudioCue.open()
        musicAudioCue.open()
        musicAudioCue.play()
        musicAudioCue.setVolume(musicAudioCue.obtainInstance(), 0.1)
        musicAudioCue.setLooping(musicAudioCue.obtainInstance(), -1)
        return showWindow(renderer)
    }

    /**
     * Erstellt ein neues Spiel
     * @param playerX Die X-Koordinate wo der Spieler gespawnt wird
     * @param playerY Die Y-Koordinate wo der Spieler gespawnt wird
     * @param diamondsInGame wie viele Diamanten im Spiel sein sollen
     */
    private fun createGame(playerX : Int, playerY : Int, diamondsInGame : Int): Game {
        val world = World(20, 20)
        createItems(world, (20..50), playerX, playerY) { Rock() }
        createItems(world, (20..50), playerX, playerY) { Monster() }
        createItems(world, (diamondsInGame..diamondsInGame), playerX, playerY) { Diamond() }
        world.setField(Coord(playerX, playerY), Player())
        return Game(world, diamondsInGame)
    }

    /**
     * erstellt die Items für ein Spiel
     * @param world Die Welt, wo die Items gespawnt werden
     * @param itemCountRange Wie viele Items gespawnt werden
     * @param playerX Die X-Koordinate wo der Spieler steht
     * @param playerY Die Y-Koordinate wo der Spieler steht
     * @param itemFactory Welches Item gespawnt wird
     */
    private fun <T : WorldItem> createItems(world: World, itemCountRange: IntRange, playerX: Int, playerY: Int, itemFactory: () -> T) {
        val itemCount = Random.nextInt(itemCountRange)
        (1..itemCount).forEach {
            world.setField(Coord(getValidXCoordinate(world, playerX), getValidYCoordinate(world, playerY)), itemFactory())
        }
    }

    /**
     * findet eine gültige Y-Koordinate, wo Items spawnen können
     * (gültig heißt: nicht neben dem Spieler)
     * @param world die Welt, wo eine gültige Koordinate gesucht wird
     * @param playerY Die Y-Koordinate wo der Spieler steht
     */
    private fun getValidYCoordinate(world: World, playerY: Int): Int {
        while (true) {
            val y = Random.nextInt(world.validYRange)
            if (y in playerY-1..playerY+1) {
                continue
            } else {
                return y
            }
        }
    }

    /**
     * findet eine gültige X-Koordinate, wo Items spawnen können
     * (gültig heißt: nicht neben dem Spieler)
     * @param world die Welt, wo eine gültige Koordinate gesucht wird
     * @param playerX Die X-Koordinate wo der Spieler steht
     */
    private fun getValidXCoordinate(world: World, playerX: Int): Int {
        while (true) {
            val x = Random.nextInt(world.validXRange)
            if (x in playerX-1..playerX+1) {
                continue
            } else {
                return x
            }
        }
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
    private fun showWindow(renderer: WorldRenderer): JFrame {
        val contentPane = JPanel(BorderLayout())
        val statusPane = JPanel(GridLayout())
        val backgroundColor = Color.BLACK
        contentPane.add(statusPane, BorderLayout.SOUTH)
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
        window.setSize(1024, 1000)

        window.isVisible = true

        window.contentPane.requestFocus()
        window.contentPane.addKeyListener(keyListener)

        window.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                renderCycle?.clockStopped = true
                sfxAudioCue.close()
                musicAudioCue.close()
                window.dispose()
            }
        })
        return window
    }


    /**
     * Umrechnung von Nanosekunden in Millisekunden.
     */
    private fun nanoToMilli(nano: Long): Long = nano / 1_000_000

}