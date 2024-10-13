package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.EmptyItem
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.Graphics

/**
 * Ein Renderer, der ein WorldItem malen kann.
 */
interface WorldItemRenderer {

    companion object {
        fun getRendererForItem(item: WorldItem): WorldItemRenderer? {
            return when (item) {
                is Player -> StaticImageRenderer
                Dirt -> StaticImageRenderer
                EmptyItem -> null
                else -> null
            }
        }
    }

    fun renderItem(
        item: WorldItem,
        renderX: Int,
        renderY: Int,
        renderWidth: Int,
        renderHeight: Int,
        g: Graphics,
        worldRenderer: WorldRenderer
    )
}