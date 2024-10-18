package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.space.Action
import de.glueckstobi.juganaut.bl.space.Direction

/**
 * Ein Steuer-Kommando des Spielers.
 */
sealed interface PlayerInput

/**
 * Steuer-Kommando des Spielers, um sich zu bewegen.
 * @param direction die Richtung, in die sich der Spieler bewegen will.
 */
data class PlayerMovement(val direction: Direction) : PlayerInput
/**
 * Steuer-Kommando des Spielers für die Aktionen
 * @param action die Aktion, die der Spieler ausführen will.
 */
data class PlayerActions(val action: Action): PlayerInput
