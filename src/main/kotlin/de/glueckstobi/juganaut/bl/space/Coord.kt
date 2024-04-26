package de.glueckstobi.juganaut.bl.space

data class Coord(val x: Int, val y: Int) {

    fun move(direction: Direction): Coord {
        return when (direction) {
            Direction.Up -> Coord(x, y - 1)
            Direction.Down -> Coord(x, y + 1)
            Direction.Left -> Coord(x-1, y)
            Direction.Right -> Coord(x+1, y)
        }
    }
}