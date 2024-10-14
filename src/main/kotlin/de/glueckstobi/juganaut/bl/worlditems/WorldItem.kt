package de.glueckstobi.juganaut.bl.worlditems

import de.glueckstobi.juganaut.bl.space.Direction
import kotlin.random.Random
import kotlin.random.nextInt

sealed interface WorldItem

object Dirt: WorldItem

object EmptyField: WorldItem

class Player: WorldItem

class Monster(var direction: Direction = Direction.random()): WorldItem

class Rock(var falling: Boolean = false): WorldItem
