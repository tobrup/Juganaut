package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.ui.swing.MainGui

fun main(args: Array<String>) {
    val game = createGame()
    MainGui().startPlaying(game)
}

private fun createGame(): Game {
    val world = World(50, 50)
    world.setField(Coord(25, 25), Player())
    return Game(world)
}