package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.WorldItem

class World(val width: Int, val height: Int) {

    private val fields: Array<Array<WorldItem>> = Array(height) { Array(width) { Dirt } }

    val validXRange = 0 until width
    val validYRange = 0 until height
    fun isValid(c: Coord): Boolean {
        return (c.x in validXRange) && (c.y in validYRange)
    }

    fun getField(c: Coord): WorldItem {
        return fields[c.y][c.x]
    }

    fun setField(c: Coord, item: WorldItem) {
        fields[c.y][c.x] = item
    }

    inline fun forEach(block: (c: Coord) -> Unit) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val c = Coord(x, y)
                block(c)
            }
        }
    }

    fun find(condition: (WorldItem) -> Boolean): Coord? {
        forEach { c ->
            if (condition(getField(c))) {
                return c
            }
        }
        return null
    }
}