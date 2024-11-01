package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.Game

/**
 * Steuer die Spiel-Runden.
 */
class TurnController(val game: Game) {

    val playerController = PlayerController(game)
    val rockController = RockController(game)
    val monsterController = MonsterController(game)
    val bombcontroller = BombController(game)

    /**
     * Wird einmal pro Runde aufgerufen, wenn die nächste Runde ausgeführt werden soll.
     */
    fun tick() {
        playerController.applyPlayerInput()
        rockController.rocksFall()
        monsterController.monstersMove()
        bombcontroller.bombsFall()
        bombcontroller.bombsTick()
    }

}