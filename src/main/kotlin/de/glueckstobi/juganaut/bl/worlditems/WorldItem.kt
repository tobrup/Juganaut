package de.glueckstobi.juganaut.bl.worlditems

import de.glueckstobi.juganaut.bl.space.Direction

/**
 * Die verschiedenen Elementen auf den Feldern des Spiels.
 */
sealed interface WorldItem

/**
 * Erde.
 */
object Dirt : WorldItem

/**
 * Ein leeres Feld
 */
object EmptyField : WorldItem


object Diamond : WorldItem

/**
 * Der Spieler
 */
class Player : WorldItem {
    var diamondCount = 0
}

/**
 * Ein Monster
 * @param direction die Richtung, in die sich das Monster gerade bewegt
 */
class Monster() : WorldItem {
    var direction: Direction = Direction.random()
    var sleeping = true

}

/**
 * Ein Stein
 * @param falling true, wenn der Stein gerade fällt
 */
class Rock(var falling: Boolean = false) : WorldItem

