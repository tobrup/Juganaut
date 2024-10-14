package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.*
import de.glueckstobi.juganaut.bl.worlditems.EmptyField
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
                is Rock -> StaticImageRenderer
                is Monster -> StaticImageRenderer
                Dirt -> StaticImageRenderer
                EmptyField -> null
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