package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.ui.swing.MainGui

fun main(args: Array<String>) {
    val game = createGame()
    MainGui().startPlaying(game)
}

private fun createGame(): Game {
    val world = World(100, 100)
    world.playerCoord = Coord(50, 50)
    return Game(world)
}