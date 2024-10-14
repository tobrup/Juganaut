package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.*

class RockTurnController(val game: Game) {
    fun rocksFall() {
        val rocks = game.world.findAll { it is Rock }
        // sort rocks, so we start at bottom of world
        val sortedRocks = rocks.sortedWith { c1, c2 ->
            (c1.y - c2.y).takeIf { it != 0 } ?: (c1.x - c2.x)
        }
        sortedRocks.forEach {
            tryFall(it)
        }
    }

    private fun tryFall(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Rock) {
            return // should not happen
        }
        val destination = source.move(Direction.Down)
        if (!game.world.isValid(destination)) {
            return
        }
        val itemBelow = game.world.getField(destination)
        when (itemBelow) {
            EmptyField -> fall(item, source, destination)
            is Rock, Dirt -> stopFalling(item)
            is Player, is Monster -> tryHit(item, source, destination, itemBelow)
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

    private fun tryHit(rock: Rock, source: Coord, destination: Coord, targetItem: WorldItem) {
        if (rock.falling) {
            // oh, oh, player or monster is hit by rock
            fall(rock, source, destination)
            if (targetItem is Player) {
                game.gameOver(RockHitsPlayer(source, destination))
            }
        }
        // otherwise it's ok, rock is just laying on the head
    }

}