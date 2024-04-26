package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.Color
import java.awt.Graphics

object PlayerRenderer: WorldItemRenderer {

    override fun renderItem(
        item: WorldItem,
        renderX: Int,
        renderY: Int,
        renderWidth: Int,
        renderHeight: Int,
        g: Graphics
    ) {
        g.color = Color.RED
        g.drawOval(renderX, renderY, renderWidth, renderHeight)
    }
}