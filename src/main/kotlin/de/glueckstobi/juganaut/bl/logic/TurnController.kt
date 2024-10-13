package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game

class TurnController(val game: Game) {

    val playerTurnController = PlayerTurnController(game)
    val rockTurnController = RockTurnController(game)

    fun tick() {
        playerTurnController.applyPlayerInput()
        rockTurnController.rocksFall()
//        monstersMove()
    }


}