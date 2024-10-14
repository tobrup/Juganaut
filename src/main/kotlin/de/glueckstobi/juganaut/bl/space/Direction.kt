package de.glueckstobi.juganaut.bl.space

import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Die möglichen Richtungen.
 */
enum class Direction {

    /**
     * Oben
     */
    Up,

    /**
     * Unten
     */
    Down,

    /**
     * Links
     */
    Left,

    /**
     * Rechts
     */
    Right;

    companion object {

        /**
         * Gibt eine zufällige Richtung zurück.
         */
        fun random(): Direction = Direction.entries[Random.nextInt(0 until Direction.entries.size)]
    }
}