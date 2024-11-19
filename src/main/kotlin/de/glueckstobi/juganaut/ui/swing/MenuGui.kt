package de.glueckstobi.juganaut.ui.swing

import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.uiDesigner.core.Spacer
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.plaf.FontUIResource
import javax.swing.text.StyleContext


class MenuGui(playerX: Int, playerY: Int, diamondsInGame: Int ) : JFrame() {
    private var contentPane: JPanel = JPanel(GridLayoutManager(4, 1, Insets(100,100,100,100), -1, -1))
    private var nameLabel: JLabel = JLabel("Juganaut", SwingConstants.CENTER)
    private var quitButton: JButton = JButton("QUIT")
    private var buttonPane: JPanel = JPanel(GridLayoutManager(1, 2))
    private var startButton: JButton = JButton("START")


    init {
        setupUI()

        setContentPane(contentPane)

        title = "Juganaut"

        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(1024, 1000)

        isVisible = true

        getContentPane().requestFocus()

        quitButton.addActionListener { e: ActionEvent? -> dispose() }

        startButton.addActionListener { e: ActionEvent? ->
            dispose()
            MainGui().startPlaying(playerX, playerY, diamondsInGame)
        }
    }
private fun setupUI() {
    contentPane = JPanel()
    contentPane.layout = GridLayoutManager(4, 1, Insets(100, 100, 100, 100), -1, -1)
    contentPane.background = Color(-13618892)
    contentPane.foreground = Color(-12535550)
    contentPane.preferredSize = Dimension(1024, 700)
    nameLabel = JLabel()
    val nameLabelFont = this.getFont("JetBrains Mono", Font.ITALIC, 48, nameLabel.font)
    if (nameLabelFont != null) nameLabel.font = nameLabelFont
    nameLabel.foreground = Color(-1446929)
    nameLabel.horizontalAlignment = 0
    nameLabel.text = "Juganaut"
    nameLabel.isVisible = true
    contentPane.add(
        nameLabel,
        GridConstraints(
            0,
            0,
            1,
            1,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false
        )
    )
    buttonPane = JPanel()
    buttonPane.layout = GridLayoutManager(1, 2, Insets(0, 0, 0, 0), -1, -1)
    buttonPane.background = Color(-13618892)
    contentPane.add(
        buttonPane,
        GridConstraints(
            2,
            0,
            1,
            1,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            null,
            null,
            null,
            0,
            false
        )
    )
    startButton = JButton()
    startButton.background = Color(-12535550)
    startButton.isEnabled = true
    val startButtonFont = this.getFont("JetBrains Mono", Font.BOLD, 20, startButton.font)
    if (startButtonFont != null) startButton.font = startButtonFont
    startButton.foreground = Color(-13618892)
    startButton.text = "START"
    buttonPane.add(
        startButton,
        GridConstraints(
            0,
            0,
            1,
            1,
            GridConstraints.ANCHOR_NORTH,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false
        )
    )
    quitButton = JButton()
    quitButton.background = Color(-65469)
    val quitButtonFont = this.getFont("JetBrains Mono", Font.BOLD, 20, quitButton.font)
    if (quitButtonFont != null) quitButton.font = quitButtonFont
    quitButton.foreground = Color(-13618892)
    quitButton.text = "QUIT"
    buttonPane.add(
        quitButton,
        GridConstraints(
            0,
            1,
            1,
            1,
            GridConstraints.ANCHOR_NORTH,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false
        )
    )
    val spacer1 = Spacer()
    contentPane.add(
        spacer1,
        GridConstraints(
            1,
            0,
            1,
            1,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_VERTICAL,
            1,
            GridConstraints.SIZEPOLICY_WANT_GROW,
            null,
            null,
            null,
            0,
            false
        )
    )
    val spacer2 = Spacer()
    contentPane.add(
        spacer2,
        GridConstraints(
            3,
            0,
            1,
            1,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_VERTICAL,
            1,
            GridConstraints.SIZEPOLICY_WANT_GROW,
            null,
            null,
            null,
            0,
            false
        )
    )
}

    @Suppress("SameParameterValue")
    private fun getFont(fontName: String?, style: Int, size: Int, currentFont: Font?): Font? {
        if (currentFont == null) return null
        val resultName: String
        if (fontName == null) {
            resultName = currentFont.name
        } else {
            val testFont = Font(fontName, Font.PLAIN, 10)
            resultName = if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                fontName
            } else {
                currentFont.name
            }
        }
        val font =
            Font(resultName, if (style >= 0) style else currentFont.style, if (size >= 0) size else currentFont.size)
        val isMac = System.getProperty("os.name", "").lowercase().startsWith("mac")
        val fontWithFallback = if (isMac) Font(font.family, font.style, font.size) else StyleContext().getFont(
            font.family,
            font.style,
            font.size
        )
        return if (fontWithFallback is FontUIResource) fontWithFallback else FontUIResource(fontWithFallback)
    }
}