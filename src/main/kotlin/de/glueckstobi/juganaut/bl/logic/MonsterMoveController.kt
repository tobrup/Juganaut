package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.*

class MonsterMoveController(val game: Game) {
    fun monstersMove() {
        val monsters = game.world.findAll { it is Monster }
        val shuffledMonsters = monsters.shuffled()
        shuffledMonsters.forEach {
            tryMove(it)
        }
    }

    private fun tryMove(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Monster) {
            return // should not happen
        }
        val destination = source.move(item.direction)
        if (canMove(destination)) {
            val otherItem = game.world.getField(destination)
            return when (otherItem) {
                is Player -> catchPlayer(item, source, destination)
                EmptyField -> move(item, source, destination)
                else -> {}// should not happen
            }
        } else {
            turn(item, source)
        }
    }

    private fun canMove(destination: Coord): Boolean {
        if (!game.world.isValid(destination)) {
            return false
        }
        val otherItem = game.world.getField(destination)
        return when (otherItem) {
            is Player, EmptyField -> true
            else -> false
        }
    }

    private fun catchPlayer(item: Monster, source: Coord, destination: Coord) {
        move(item, source, destination)
        game.gameOver(MonsterCatchesPlayer(source, destination))
    }

    private fun move(item: Monster, source: Coord, destination: Coord) {
        game.world.setField(source, EmptyField)
        game.world.setField(destination, item)
    }

    private fun turn(item: Monster, c: Coord) {
        item.direction = getNewDirection(item, c)
    }

    private fun getNewDirection(monster: Monster, source: Coord): Direction {
        var nextDirection = Direction.random()
        (0..4).forEach {
            val destination = source.move(nextDirection)
            if (canMove(destination)) {
                return nextDirection
            }
            val nextDirectionIndex = (nextDirection.ordinal + 1) % Direction.entries.size
            nextDirection = Direction.entries[nextDirectionIndex]
        }
        return nextDirection
    }

}