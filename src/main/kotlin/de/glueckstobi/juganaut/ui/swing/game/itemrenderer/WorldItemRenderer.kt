package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.*
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.Graphics

/**
 * Die Schnittstelle für ein Objekt, das ein Spiel-Element auf den Bildschirm malen kann.
 */
interface WorldItemRenderer {

    companion object {

        /**
         * Gibt den richtigen Renderer für das angegebene Spiel-Element zurück.
         * Wenn es keinen Renderer gibt, wird null zurückgegeben.
         */
        fun getRendererForItem(item: WorldItem): WorldItemRenderer? {
            return when (item) {
                is Player -> StaticImageRenderer
                is Rock -> StaticImageRenderer
                is Diamond -> StaticImageRenderer
                is Monster -> StaticImageRenderer
                Dirt -> StaticImageRenderer
                EmptyField -> null
            }
        }
    }

    /**
     * Malt das Spiel-Element auf den Bildschirm.
     * @param item das Spiel-Element, das gemalt werden soll
     * @param renderX die X-Koordinate (horizontal) im Fenster, an der das Element gemalt werden soll
     * @param renderY die Y-Koordinate (vertikal) im Fenster, an der das Element gemalt werden soll
     * @param renderWidth die Breite, in der Spiel-Element gemalt werden soll
     * @param renderHeight die Höhe, in der Spiel-Element gemalt werden soll
     * @param g ein Graphics-Objekt, das zum Malen verwendet werden kann
     * @param worldRenderer der WorldRenderer selbst (bietet Funktionen, um Bilder zu malen)
     *
     */
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