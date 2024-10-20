package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Monster
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.bl.worlditems.Rock
import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import de.glueckstobi.juganaut.ui.swing.MainGui
import kotlin.random.Random
import kotlin.random.nextInt

fun main(args: Array<String>) {
    val game = createGame()
    MainGui().startPlaying(game)
}

private fun createGame(): Game {
    val world = World(20, 20)
    createItems(world, (20..50)) { Rock() }
    createItems(world, (20..50)) { Monster() }
    world.setField(Coord(10, 10), Player())
    return Game(world)
}

private fun <T : WorldItem> createItems(world: World, itemCountRange: IntRange, itemFactory: () -> T) {
    val itemCount = Random.nextInt(itemCountRange)
    (1..itemCount).forEach {
        val x = Random.nextInt(world.validXRange)
        val y = Random.nextInt(world.validYRange)
        world.setField(Coord(x, y), itemFactory())
    }
}
