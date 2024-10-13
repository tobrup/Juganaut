package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.*

class RockTurnController(val game: Game) {
    fun rocksFall() {
        // start at bottom of world!
        game.world.validYRange.sortedDescending().forEach { y ->
            game.world.validXRange.forEach { x ->
                tryFall(Coord(x, y))
            }
        }
    }

    private fun tryFall(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Rock) {
            return
        }
        val destination = source.move(Direction.Down)
        if (!game.world.isValid(destination)) {
            return
        }
        val itemBelow = game.world.getField(destination)
        when (itemBelow) {
            EmptyField -> fall(item, source, destination)
            is Rock, Dirt -> stopFalling(item)
            is Player -> tryHitPlayer(item, source, destination)
        }
    }

    private fun fall(rock: Rock, source: Coord, destination: Coord) {
        rock.falling = true
        game.world.setField(source, EmptyField)
        game.world.setField(destination, rock)
    }

    private fun stopFalling(rock: Rock) {
        rock.falling = false
    }

    private fun tryHitPlayer(rock: Rock, source: Coord, destination: Coord) {
        if (rock.falling) {
            // oh, oh, player is hit by rock
            fall(rock, source, destination)
            game.gameOver(RockHitsPlayer(source, destination))
        }
        // otherwise it's ok, rock is just laying on players head
    }

}