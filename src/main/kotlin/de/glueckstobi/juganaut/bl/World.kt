package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.WorldItem

/**
 * Enthält das Spielfeld mit allen Elementen auf den einzelnen Feldern.
 * @param width die Breite des Spielfelds
 * @param height die Höhe des Spielfelds.
 */
class World(val width: Int, val height: Int) {

    /**
     * Die einzelnen Felder.
     *
     * Eine 2-dimensionale Liste (d.h. eine Liste voller Listen).
     * Die äußere Liste enthält für jede Zeile eine weitere Liste.
     * Die Zeilen-Listen enthalten für jedes Feld das entsprechende [WorldItem] des Feldes.
     */
    private val fields: List<MutableList<WorldItem>> = List(height) { MutableList(width) { Dirt } }

    /**
     * Der gültige Bereich der X-Koordinaten
     */
    val validXRange = 0 until width

    /**
     * Der gültige Bereich der Y-Koordinaten
     */
    val validYRange = 0 until height

    /**
     * Gibt true zurück, wenn die angegebene Koordinate gültig ist, d.h. innerhalb des Spielfelds liegt.
     */
    fun isValid(c: Coord): Boolean {
        return (c.x in validXRange) && (c.y in validYRange)
    }

    /**
     * Gibt das Spiel-Element an der angegenben Koordinate zurück
     */
    fun getField(c: Coord): WorldItem {
        return fields[c.y][c.x]
    }

    /**
     * Setzt das angegebene Spiel-Element an der angegebenen Koordinate
     */
    fun setField(c: Coord, item: WorldItem) {
        fields[c.y][c.x] = item
    }

    /**
     * Geht alle Koordinaten durch und ruft den angegebenen Block für jede Koordinate einmal auf
     * und übergibt ihm dabei die jeweilige Koordinate.
     */
    inline fun forEach(block: (c: Coord) -> Unit) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val c = Coord(x, y)
                block(c)
            }
        }
    }

    /**
     * Findet ein Spiel-Element, das die angegebene Bedingung erfüllt.
     * Wenn kein solches Element gefunden wird, wird null zurückgegeben.
     */
    fun find(condition: (WorldItem) -> Boolean): Coord? {
        return findAll(condition).firstOrNull()
    }

    /**
     * Findet alle Spiel-Elemente, die die angegebene Bedingung erfüllen
     * und gibt diese Liste zurück.
     */
    fun findAll(condition: (WorldItem) -> Boolean): List<Coord> {
        val result = mutableListOf<Coord>()
        forEach { c ->
            if (condition(getField(c))) {
                result.add(c)
            }
        }
        return result
    }
}