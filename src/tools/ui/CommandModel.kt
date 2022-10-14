package tools.ui

data class CommandModel(val key: String, val value: String)

data class CommandModelList(val data: List<CommandModel>? = null)