package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.*
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
    createItems(world, 10..20) { Diamond }
    createItems(world, (10..20)) { Bomb() }

    val playerCoord = Coord(10, 10)
    world.setField(playerCoord, Player())
    val oben = playerCoord.move(Direction.Up)
    val links = playerCoord.move(Direction.Left)
    val unten = playerCoord.move(Direction.Down)
    val rechts = playerCoord.move(Direction.Right)
    world.setField(oben, Dirt)
    world.setField(links, Dirt)
    world.setField(unten, Dirt)
    world.setField(rechts, Dirt)
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
