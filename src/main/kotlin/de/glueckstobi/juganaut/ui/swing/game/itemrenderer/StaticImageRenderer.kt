package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.*
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon

object StaticImageRenderer: WorldItemRenderer {

    private val playerImage = loadImage("/doggy.png")
    private val dirtImage = loadImage("/dirt.png")
    private val rockImage = loadImage("/rock.png")

    private fun loadImage(filename: String): Image {
        val rawImage = ImageIcon(javaClass.getResource(filename)).image
        val scaledImage = rawImage.getScaledInstance(WorldRenderer.fieldRenderSize, WorldRenderer.fieldRenderSize, 0)
        return scaledImage
    }

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

    private fun getImage(item: WorldItem): Image? {
        return when (item) {
            is Player -> playerImage
            is Rock -> rockImage
            Dirt -> dirtImage
            EmptyField -> null
        }
    }
}