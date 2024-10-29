package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.*

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
            if (game.world.isValid(c)) {
                val item = game.world.getField(c)
                when (item) {
                    is Bomb -> activateBomb(bomb, item, c)
                    Diamond -> clearField(c)
                    Dirt -> clearField(c)
                    EmptyField -> {}
                    is Monster -> clearField(c)
                    is Player -> killPlayer(item, c)
                    is Rock -> clearField(c)
                }
            }

        }
    }

    private fun clearField(c: Coord) {
        game.world.setField(c, EmptyField)
    }

    fun activateBomb(explosionbomb: Bomb, destinationBomb: Bomb, c: Coord) {
        if (explosionbomb != destinationBomb) {
            destinationBomb.active = true
        } else {
            clearField(c)
        }
    }

    fun killPlayer(item: Player, c: Coord) {
        game.gameOver(Explosion())
        clearField(c)
    }
}
