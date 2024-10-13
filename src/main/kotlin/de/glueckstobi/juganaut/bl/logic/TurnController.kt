package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.EmptyItem
import de.glueckstobi.juganaut.bl.worlditems.Player

class TurnController(val game: Game) {

    private var pausing = false

    private var pendingUserInput: PlayerInput? = null
    fun setPlayerInput(playerInput: PlayerInput) {
        pendingUserInput = playerInput
    }

    fun tick() {
        if (pausing) {
            return
        }
        applyPlayerInput()
        if (pausing) {
            return
        }
//        monstersMove()
//        rocksFall()
    }

    private fun applyPlayerInput() {
        val input = pendingUserInput
        pendingUserInput = null
        when (input) {
            Pause -> pausing = !pausing
            is PlayerMovement -> tryMovePlayer(input.direction)
            null -> {}
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
            EmptyItem, Dirt -> movePlayer(source, destination)
            is Player -> {} // player moves to itself? should not happen
            else -> {}
        }
    }

    private fun movePlayer(source: Coord, destination: Coord) {
        val player = game.world.getField(source)
        game.world.setField(source, EmptyItem)
        game.world.setField(destination, player)
    }
}