package tools

import com.google.gson.Gson
import tools.MainDev.mCurrentProcess
import tools.ui.*
import java.awt.FlowLayout
import java.awt.ScrollPane
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.swing.*

const val CONFIG_FILE = "command.json"
const val KEY_CLEAR_LOG = "clear_log"

object MainDev {
    var mCurrentProcess = ArrayList<Process?>()

    @JvmStatic
    fun main(args: Array<String>) {
       showImp()
    }

    fun exec(command: String) {
        val process = Runtime.getRuntime().exec(command)
        Thread {
            mCurrentProcess.add(process)
            process?.inputStream?.let { inputSteam ->
                val br = BufferedReader(InputStreamReader(inputSteam))
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    println(line)
                }
            }
            mCurrentProcess.remove(process)
        }.start()
    }

    fun showImp() {
        val window = DefaultJFrame("Alias脚本工具").also {
            it.setSize(1080, 760)
        }
        window.contentPane = JPanel(FlowLayout())
        addClearLogcat(window)
        addStopCommand(window)
//        addConfigFile(window)
        val all = getCommandGson(getJsonString())
        all?.forEach { item ->
            addNormalButton(window, item) {
                mLogTextArea?.append(it)
                mLogTextArea?.append("\n")
            }
        }
        addLogcat(window)
        window.showFrameOnCenter()
    }

    private fun addClearLogcat(window: JFrame) {
        val clearLog = CommandModel("Clear Logcat", KEY_CLEAR_LOG)
        addNormalButton(window, clearLog, action = {
            mLogTextArea?.clear()
        })
    }

    private fun addStopCommand(window: JFrame) {
        val clearLog = CommandModel("Stop Command", KEY_CLEAR_LOG)
        addNormalButton(window, clearLog, action = {
            mCurrentProcess.forEach {
                it?.destroy()
            }
            mCurrentProcess.clear()
        })
    }

    private fun addConfigFile(window: JFrame) {
        val command = "gedit ${File(CONFIG_FILE).absolutePath}"
        val configFile = CommandModel("Edit Configuration", command)
        addNormalButton(window, configFile) {
            mLogTextArea?.append(it)
            mLogTextArea?.append("\n")
        }
    }

    private fun getJsonString(): String? {
        return FileUtils.readString(CONFIG_FILE)
    }

    private fun getCommandGson(jsonString: String? = null): List<CommandModel>? {
        if (jsonString.isNullOrEmpty()) {
            return null
        }
        val gson = Gson()
        val data = gson.fromJson(jsonString, CommandModelList::class.java)
        return data.data
    }

    private fun addNormalButton(
        window: JFrame,
        item: CommandModel,
        action: ActionListener? = null,
        listener: ((String) -> Unit)? = null
    ) {
        val buttonJFrame = JPanel()
        val button = JButton(item.key)
        if (action == null) {
            button.addActionListener(TerminalListener(item.value, listener))
        } else {
            button.addActionListener(action)
        }
        buttonJFrame.add(button)
        window.add(buttonJFrame)
    }

    private var mLogTextArea: TextScrollPane? = null

    private fun addLogcat(window: JFrame) {
        mLogTextArea = TextScrollPane(window)
        mLogTextArea?.setSize(1060, 250)
        mLogTextArea?.append("欢迎使用本公司智能产品!!")
        mLogTextArea?.append("\n配置文件:${File(CONFIG_FILE).absolutePath}")
    }
}

class TerminalListener(private val command: String, private val listener: ((String) -> Unit)?) : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        try {
            listener?.invoke("\n开始执行命令:${command}")
            val process = Runtime.getRuntime().exec(command)
            Thread {
                mCurrentProcess.add(process)
                process?.inputStream?.let { inputSteam ->
                    val br = BufferedReader(InputStreamReader(inputSteam))
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        listener?.invoke(line ?: "empty")
                    }
                }
                mCurrentProcess.remove(process)
            }.start()
        } catch (e: Exception) {
            showError(e.message)
        }
    }

    private fun showError(errorMessage: String? = "default") {
        val dialog = DefaultJDialog("执行错误")
        dialog.setSize(300, 200)
        val label = JLabel("命令执行:${errorMessage}")
        dialog.add(label)
        dialog.showFrameOnCenter()
    }

}