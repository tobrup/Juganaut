package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.*

class BombController(val game: Game) {


    fun bombsTick() {
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


    /**
     * Lässt alle Bomben nach unten fallen.
     * Wird einmal pro Runde aufgerufen.
     */
    fun bombsFall() {
        val bombs = game.world.findAll { it is Bomb }
        // sort bombs, so we start with the lowest bombs
        val sortedBombs = bombs.sortedWith { c1, c2 ->
            (c1.y - c2.y).takeIf { it != 0 } ?: (c1.x - c2.x)
        }
        sortedBombs.forEach {
            tryFall(it)
        }
    }

    /**
     * Versucht, die Bombe nach unten fallen zu lassen.
     * @param source Koordinate einer Bombe.
     */
    private fun tryFall(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Bomb) {
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
            is Player, is Monster -> stopFalling(item)
            is Bomb -> stopFalling(item)
            Diamond -> stopFalling(item)
        }
    }

    /**
     * Der Stein fällt 1 Feld nach unten.
     * @param rock der Bombe
     * @param source die Ursprungs-Koordinate
     * @param destination die Ziel-Koordinate
     */
    private fun fall(bomb: Bomb, source: Coord, destination: Coord) {
        bomb.falling = true
        game.world.setField(source, EmptyField)
        game.world.setField(destination, bomb)
    }

    /**
     * Der Stein fällt nicht mehr weiter.
     * @param rock der Stein
     */
    private fun stopFalling(bomb: Bomb) {
        if (bomb.falling) {
            bomb.active = true
        }
        bomb.falling = false
    }


}
