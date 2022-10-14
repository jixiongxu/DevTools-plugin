package tools.ui

import java.awt.Color
import java.awt.Toolkit
import javax.swing.JDialog

class DefaultJDialog(titleString: String) : JDialog() {

    init {
        this.title = titleString
        this.background = Color.DARK_GRAY
        this.setLocationRelativeTo(null)
        this.defaultCloseOperation = HIDE_ON_CLOSE
    }

    private fun showFrame() {
        this.isVisible = true
    }

    fun showFrameOnCenter() {
        val toolkit = Toolkit.getDefaultToolkit()
        val screenSize = toolkit.screenSize
        val x = (screenSize.width - width) / 2
        val y = (screenSize.height - height) / 2
        setLocation(x, y)
        showFrame()
    }
}