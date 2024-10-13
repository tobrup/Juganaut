package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.logic.TurnController
import de.glueckstobi.juganaut.bl.worlditems.WorldItem

class Game(val world: World) {

    val turnController = TurnController(this)
}