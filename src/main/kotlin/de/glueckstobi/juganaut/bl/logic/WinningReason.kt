package de.glueckstobi.juganaut.bl.logic

/**
 * Die verschiedenen Gründe für einen Gewinn
 */
sealed interface WinningReason

/**
 * Alle Diamanten sind eingesammelt
 */
class AllDiamondsCollected(diamondsCollected: Int) : WinningReason