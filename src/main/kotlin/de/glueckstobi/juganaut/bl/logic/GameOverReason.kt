package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.space.Coord

sealed interface GameOverReason

class RockHitsPlayer(rockCoord: Coord, playerCoord: Coord): GameOverReason

class PlayerWalksIntoMonster(playerCoord: Coord, monsterCoord: Coord): GameOverReason

class MonsterCatchesPlayer(monsterCoord: Coord, playerCoord: Coord): GameOverReason