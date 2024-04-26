package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.EmptyItem
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.bl.worlditems.WorldItem

class World(val width: Int, val height: Int) {

    val player = Player()

    var playerCoord = Coord(0, 0)

    fun getItem(c: Coord): WorldItem {
        return when (c) {
            playerCoord -> player
            else -> EmptyItem
        }
    }
}