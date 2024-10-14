package de.glueckstobi.juganaut.bl.space

import kotlin.random.Random
import kotlin.random.nextInt

enum class Direction {

    Up, Down, Left, Right;

    companion object {
        fun random(): Direction = Direction.entries[Random.nextInt(0 until Direction.entries.size)]
    }
}