package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.EmptyField
import de.glueckstobi.juganaut.bl.worlditems.Player

class TurnController(val game: Game) {

    private var playerInput: PlayerInput? = null
    private var playerInputPressed = false

    fun playerInputPressed(input: PlayerInput) {
        playerInput = input
        playerInputPressed = true
    }

    fun playerInputReleased() {
        playerInputPressed = false
    }


    fun tick() {
        applyPlayerInput()
//        monstersMove()
//        rocksFall()
    }

    private fun applyPlayerInput() {
        val currentInput = playerInput
        if (currentInput == null) {
            return
        }
        if (!playerInputPressed) {
            playerInput = null
        }
        when (currentInput) {
            is PlayerMovement -> tryMovePlayer(currentInput.direction)
        }
    }


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
            is Player -> {} // player moves to itself? should not happen
        }
    }

    private fun movePlayer(source: Coord, destination: Coord) {
        val player = game.world.getField(source)
        game.world.setField(source, EmptyField)
        game.world.setField(destination, player)
    }

}