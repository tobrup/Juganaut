package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.*
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon

/**
 * Zeichnet ein Spiel als statisches Bild auf dem Bildschirm.
 */
object StaticImageRenderer : WorldItemRenderer {

    private val playerImage = loadImage("/textures/cat.png")
    private val dirtImage = loadImage("/textures/dirt.png")
    private val rockImage = loadImage("/textures/rock.png")
    private val monsterImage = loadImage("/textures/monster.png")
    private val diamondImage = loadImage("/textures/diamond.png")

    /**
     * Lädt das Bild mit dem angegebenen Datei-Namen.
     */
    fun loadImage(filename: String): Image {
        val rawImage = ImageIcon(javaClass.getResource(filename)).image
        val scaledImage = rawImage.getScaledInstance(WorldRenderer.fieldRenderSize, WorldRenderer.fieldRenderSize, 0)
        return scaledImage
    }

    /**
     * Malt das angegebene Element auf den Bildschirm, wenn möglich.
     */
    override fun renderItem(
        item: WorldItem,
        renderX: Int,
        renderY: Int,
        renderWidth: Int,
        renderHeight: Int,
        g: Graphics,
        worldRenderer: WorldRenderer
    ) {
        val image = getImage(item)
        image?.let {
            g.drawImage(image, renderX, renderY, worldRenderer)
        }
    }

    /**
     * Gibt das richtige Bild für das angegebene Spiel-Element zurück.
     * Wenn es kein Bild dafür gibt, wird null zurückgegeben.
     */
    private fun getImage(item: WorldItem): Image? {
        return when (item) {
            is Player -> playerImage
            is Rock -> rockImage
            is Monster -> monsterImage
            is Diamond -> diamondImage
            Dirt -> dirtImage
            EmptyField -> null
        }
    }
}