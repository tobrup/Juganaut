package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.*
import de.glueckstobi.juganaut.ui.swing.MainGui
import kotlin.random.Random
import kotlin.random.nextInt


fun main(args: Array<String>) {
//    if (args[0] == "" || args[1] == "") {
//
//    }

    val playerX = args[0].toInt()
    val playerY = args[1].toInt()
    val diamondsInGame = args[2].toInt()
    val game = createGame(playerX, playerY, diamondsInGame)

    MainGui().startPlaying(game)

}

private fun createGame(playerX : Int, playerY : Int, diamondsInGame : Int): Game {
    val world = World(20, 10)
    createItems(world, (20..50), playerX, playerY) { Rock() }
    createItems(world, (20..50), playerX, playerY) { Monster() }
    createItems(world, (diamondsInGame..diamondsInGame), playerX, playerY) { Diamond() }
    world.setField(Coord(playerX, playerY), Player())
    return Game(world, diamondsInGame)
}

private fun <T : WorldItem> createItems(world: World, itemCountRange: IntRange,playerX: Int, playerY: Int, itemFactory: () -> T) {
    val itemCount = Random.nextInt(itemCountRange)
    (1..itemCount).forEach {
        world.setField(Coord(getValidXCoordinate(world, playerX), getValidYCoordinate(world, playerY)), itemFactory())
    }
}
private fun getValidYCoordinate(world: World, playerY: Int): Int {
    while (true) {
        val y = Random.nextInt(world.validYRange)
        if (y in playerY-1..playerY+1) {
            continue
        } else {
            return y
        }
    }
}
private fun getValidXCoordinate(world: World, playerX: Int): Int {
    while (true) {
        val x = Random.nextInt(world.validXRange)
        if (x in playerX-1..playerX+1) {
            continue
        } else {
            return x
        }
    }
}