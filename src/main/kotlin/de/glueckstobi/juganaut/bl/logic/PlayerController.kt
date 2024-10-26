package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.*

/**
 * Verarbeitet die Steuer-Kommandos des Spielers und
 * berechnet die Bewegungen und Aktionen des Spielers.
 */
class PlayerController(val game: Game) {

    /**
     * Der aktuell aktive Steuer-Kommando des Spielers.
     * Wenn gerade kein Kommando aktiv ist, ist der Wert null.
     */
    private var playerInput: PlayerInput? = null

    /**
     * true, wenn der Spieler gerade die Taste des [playerInput] gedrückt hält.
     */
    private var playerInputPressed = false

    /**
     * true, wenn das [playerInput] bereits einmal ausgeführt wurde.
     */
    private var playerInputProcessed = false

    /**
     * Wird aufgerufen, wenn der Spieler eine Taste drückt.
     * @param input das Steuer-Kommando der gedrückten Taste
     */
    fun playerInputPressed(input: PlayerInput) {
        playerInput = input
        playerInputPressed = true
        playerInputProcessed = false
    }

    /**
     * Wird gedrückt, wenn der Spieler die Taste wieder loslässt.
     */
    fun playerInputReleased() {
        playerInputPressed = false
        if (playerInputProcessed) {
            // input was already applied at least once, so we can reset it immediately
            // otherwise wait until it's applied at least once.
            playerInput = null
        }
    }

    /**
     * Führt das aktuelle Steuer-Kommando aus.
     * Wird einmal pro Runde aufgerufen.
     */
    fun applyPlayerInput() {
        val currentInput = playerInput
        if (currentInput == null) {
            return
        }
        if (!playerInputPressed) {
            // key is not pressed and the input is pressed at least once (or the last time),
            // so reset the input to null
            playerInput = null
        }
        playerInputProcessed = true
        when (currentInput) {
            is PlayerMovement -> tryMovePlayer(currentInput.direction)
        }
    }


    /**
     * Versucht, den Spieler in die angegebene Richtung zu bewegen.
     * @param direction Richtung, in die der Spieler bewegt werden soll.
     */
    private fun tryMovePlayer(direction: Direction) {
        val source = game.world.find { it is Player }
        if (source == null) {
            // no player found. should not happen
            return
        }
        val destination = source.move(direction)
        if (!game.world.isValid(destination)) {
            return
        }
        val destinationItem = game.world.getField(destination)
        when (destinationItem) {
            EmptyField, Dirt -> movePlayer(source, destination)
            is Monster -> moveIntoMonster(source, destination)
            is Rock -> tryMoveRock(destination, direction, source, destinationItem) // can not move, do nothing
            is Player -> {} // player moves to itself? should not happen
            Diamond -> collectDiamond ( source,destination)
        }
    }

    fun collectDiamond(source: Coord,destination: Coord) {
        val player = game.world.getField(source) as Player

        movePlayer(source,destination)
        val newCount = player.diamondCount+1
        player.diamondCount = newCount
    }
    fun tryMoveRock(rockCoord: Coord, direction: Direction, playerCoord: Coord, rock: Rock) {
        if (rock.falling) {
            return
        }

        val next = rockCoord.move(direction)

        if (!game.world.isValid(next)) {
            return
        }

        val nextItem = game.world.getField(next)
        when (nextItem) {
            is Rock -> return
            is Monster -> return
            Dirt -> return
            is Player -> return //should not happen
            Diamond -> return
            EmptyField -> moveRock(rockCoord, rock, playerCoord, next)
        }
    }


    fun moveRock(rockCoord: Coord, rock: Rock, playerCoord: Coord, next: Coord) {
        val player = game.world.getField(playerCoord)
        game.world.setField(next, rock)
        game.world.setField(rockCoord, player)
        game.world.setField(playerCoord, EmptyField)
    }

    /**
     * Bewegt den Spieler.
     * @param source Ursprungs-Koordinate
     * @param destination Ziel-Koordinate
     */
    private fun movePlayer(source: Coord, destination: Coord) {
        val player = game.world.getField(source)
        game.world.setField(source, EmptyField)
        game.world.setField(destination, player)
    }

    /**
     * Bewegt den Spieler auf das Feld eines Monster
     * @param source Ursprungs-Koordinate
     * @param destination Ziel-Koordinate
     */
    private fun moveIntoMonster(source: Coord, destination: Coord) {
        game.gameOver(PlayerWalksIntoMonster(source, destination))
    }

}