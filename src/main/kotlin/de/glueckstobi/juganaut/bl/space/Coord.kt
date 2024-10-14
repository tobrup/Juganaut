package de.glueckstobi.juganaut.bl.space

/**
 * Speicher eine Koordinate.
 * @param x die horizontale Position (0 ist ganz links)
 * @param y die vertikale Positon (0 is ganz oben)
 */
data class Coord(val x: Int, val y: Int) {

    /**
     * Erzeugt eine neue Koordinate, die um 1 Feld in der angegenen Richtung liegt.
     * @param direction die Richtung
     * @return Gibt die neue Koordinate zurück
     */
    fun move(direction: Direction): Coord {
        return when (direction) {
            Direction.Up -> Coord(x, y - 1)
            Direction.Down -> Coord(x, y + 1)
            Direction.Left -> Coord(x-1, y)
            Direction.Right -> Coord(x+1, y)
        }
    }
}