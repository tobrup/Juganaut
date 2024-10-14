package de.glueckstobi.juganaut.ui.swing.game

import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import de.glueckstobi.juganaut.ui.swing.game.itemrenderer.WorldItemRenderer
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

/**
 * Malt das ganze Spielfeld auf den Bildschirm.
 */
class WorldRenderer(val world: World) : JPanel() {

    companion object {
        /**
         * Größe eines Feldes auf dem Bildschirm
         */
        val fieldRenderSize = 30
    }

    /**
     * Wird jedes Mal aufgerufen, wenn das Spielfeld neu gemalt werden soll.
     */
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        renderWorld(g)
    }

    /**
     * Malt das Spielfeld.
     * @param g Graphic-Objekt, das zum malen benutzt werden kann.
     */
    private fun renderWorld(g: Graphics) {
        renderBackground(g)
        world.validYRange.forEach { y ->
            world.validXRange.forEach { x ->
                val item = world.getField(Coord(x, y))
                renderItem(item, x, y, g)
            }
        }
    }

    /**
     * Malt den Hintergrund.
     */
    private fun renderBackground(g: Graphics) {
        g.color = Color.black
        val bounds = g.clipBounds
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height)
    }

    /**
     * Malt ein einzelnes Spiel-Element.
     * @param item das Spiel-Element, das gemalt werden soll
     * @param x die X-Koordinate (horizontal), an der gemalt werden soll.
     * @param y die Y-Koordinate (vertikal), an der gemalt werden soll.
     * @param g Graphic-Objekt, das zum malen benutzt werden kann
     */
    private fun renderItem(item: WorldItem, x: Int, y: Int, g: Graphics) {
        val renderer = WorldItemRenderer.getRendererForItem(item)
        renderer?.let {
            val renderX = x * fieldRenderSize
            val renderY = y * fieldRenderSize
            it.renderItem(item, renderX, renderY, fieldRenderSize, fieldRenderSize, g, this)
        }
    }
}