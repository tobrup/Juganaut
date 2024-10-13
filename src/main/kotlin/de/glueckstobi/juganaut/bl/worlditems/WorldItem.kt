package de.glueckstobi.juganaut.bl.worlditems

sealed interface WorldItem

object Dirt: WorldItem

object EmptyField: WorldItem

class Player: WorldItem

class Rock(var falling: Boolean = false): WorldItem