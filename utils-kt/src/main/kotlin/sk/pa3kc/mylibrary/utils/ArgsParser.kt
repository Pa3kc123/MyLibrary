package sk.pa3kc.mylibrary.utils

operator fun ArgsParser.get(i: Int): String = this.args[i]
operator fun ArgsParser.get(key: String): String? = this.options[key]
operator fun ArgsParser.get(key: String, def: String): String = this.options[key] ?: def
