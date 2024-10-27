package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Bomb
import de.glueckstobi.juganaut.bl.worlditems.EmptyField

class BombController(val game: Game) {


    fun nextTurn() {
        val bomblist = game.world.findAll { it is Bomb }
        bomblist.forEach {
            tick(it)
        }
    }

    private fun tick(coord: Coord) {
        val bomb = game.world.getField(coord) as Bomb
        if (!bomb.active) {
            return
        }
        bomb.countdown = bomb.countdown - 1
        tryExplode(bomb, coord)


    }

    fun tryExplode(bomb: Bomb, coord: Coord) {
        if (bomb.countdown > 0) return
        explode(bomb, coord)
    }


    fun explode(bomb: Bomb, coord: Coord) {
        val explosionField = listOf(
            Coord(coord.x - 1, coord.y),
            Coord(coord.x, coord.y + 1),
            Coord(coord.x + 1, coord.y),
            Coord(coord.x, coord.y - 1),
            Coord(coord.x, coord.y),
            Coord(coord.x - 1, coord.y - 1),
            Coord(coord.x - 1, coord.y + 1),
            Coord(coord.x + 1, coord.y + 1),
            Coord(coord.x + 1, coord.y - 1),
        )
        explosionField.forEach { c ->
            game.world.setField(c, EmptyField)
        }
    }
}
