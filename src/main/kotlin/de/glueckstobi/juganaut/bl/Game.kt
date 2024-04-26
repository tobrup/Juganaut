package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.worlditems.WorldItem

class Game(val world: World) {

    private var items: List<WorldItem> = listOf(world.player)
}