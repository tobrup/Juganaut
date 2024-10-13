package de.glueckstobi.juganaut.ui.swing.game

import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import de.glueckstobi.juganaut.ui.swing.game.itemrenderer.WorldItemRenderer
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class WorldRenderer(val world: World): JPanel() {

    companion object {
        /**
         * Größe eines Feldes auf dem Bildschirm
         */
        val fieldRenderSize = 30
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        renderWorld(g)
    }

    private fun renderWorld(g: Graphics) {
        renderBackground(g)
        (0 until world.height).forEach { y ->
            (0 until world.width).forEach { x ->
                val item = world.getField(Coord(x, y))
                renderItem(item, x, y, g)
            }
        }
    }

    private fun renderBackground(g: Graphics) {
        g.color = Color.black
        val bounds = g.clipBounds
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height)
    }

    private fun renderItem(item: WorldItem, x: Int, y: Int, g: Graphics) {
        val renderer = WorldItemRenderer.getRendererForItem(item)
        renderer?.let {
            val renderX = x * fieldRenderSize
            val renderY = y * fieldRenderSize
            it.renderItem(item, renderX, renderY, fieldRenderSize, fieldRenderSize, g, this)
        }
    }
}