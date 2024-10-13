package de.glueckstobi.juganaut.bl

import de.glueckstobi.juganaut.bl.logic.GameOverReason
import de.glueckstobi.juganaut.bl.logic.TurnController
import de.glueckstobi.juganaut.bl.worlditems.WorldItem

class Game(val world: World) {

    var gameOverReason: GameOverReason? = null

    val turnController = TurnController(this)

    fun gameOver(reason: GameOverReason) {
        gameOverReason = reason
    }
}