package de.glueckstobi.juganaut.bl.logic

import de.glueckstobi.juganaut.bl.space.Coord

/**
 * Die verschiedenen Gründe für ein GameOver
 */
sealed interface GameOverReason

/**
 * Spieler wird von Stein getroffen
 */
class RockHitsPlayer(rockCoord: Coord, playerCoord: Coord) : GameOverReason

/**
 * Spieler läuft in ein Monster
 */
class PlayerWalksIntoMonster(playerCoord: Coord, monsterCoord: Coord) : GameOverReason

/**
 * Monster erwischt den Spieler
 */
class MonsterCatchesPlayer(monsterCoord: Coord, playerCoord: Coord) : GameOverReason