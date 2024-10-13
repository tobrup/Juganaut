package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.bl.worlditems.Rock
import de.glueckstobi.juganaut.ui.swing.MainGui
import kotlin.random.Random
import kotlin.random.nextInt

fun main(args: Array<String>) {
    val game = createGame()
    MainGui().startPlaying(game)
}

private fun createGame(): Game {
    val world = World(50, 50)
    createRocks(world)
    world.setField(Coord(10, 10), Player())
    return Game(world)
}

fun createRocks(world: World) {
    val rockCount = 20
    (1..rockCount).forEach {
        val x = Random.nextInt(world.validXRange)
        val y = Random.nextInt(world.validYRange)
        world.setField(Coord(x, y), Rock())
    }
}
