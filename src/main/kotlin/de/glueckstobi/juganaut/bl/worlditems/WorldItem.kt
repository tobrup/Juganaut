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





