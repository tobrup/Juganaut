package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.space.Direction

sealed interface PlayerInput

data class PlayerMovement(val direction: Direction): PlayerInput

object Pause: PlayerInput