package de.glueckstobi.juganaut.ui.swing.game

import java.awt.Graphics
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.JPanel


class JPanelWithBackground(fileName: URL?) : JPanel() {
    private val backgroundImage: Image? = ImageIO.read(fileName)


    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        try {
            val scaledBackgroundImage = backgroundImage?.getScaledInstance(g.clipBounds.width, g.clipBounds.height, Image.SCALE_DEFAULT)
            // Draw the background image.
            g.drawImage(scaledBackgroundImage, 0, 0, this)
        } catch (e: IllegalArgumentException) {
            return
        }
    }
}