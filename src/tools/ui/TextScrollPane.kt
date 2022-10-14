package tools.ui

import java.awt.ScrollPane
import javax.swing.JFrame
import javax.swing.JTextArea


class TextScrollPane(window: JFrame) : ScrollPane() {

    private val text = JTextArea("")

    init {
        add(text)
        window.add(this)
    }

    fun append(msg: String) {
        if ((text.text?.length ?: 0) > (Int.MAX_VALUE - 1024)) {
            clear()
        }
        text.append(msg)
        scrollEnd()
    }

    private fun scrollEnd() {
        text.caretPosition = text.document.length
        setScrollPosition(0,text.height)
    }

    fun clear() {
        text.text = ""
    }

}