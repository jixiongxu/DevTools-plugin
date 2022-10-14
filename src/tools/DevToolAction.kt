package tools

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DevToolAction : AnAction() {
    override fun actionPerformed(anActionEvent: AnActionEvent) {
        MainDev.showImp()
    }
}