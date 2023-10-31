package me.semimute.dungeonnons.util

import me.semimute.dungeonnons.Main

class Utils: Main(){
    companion object {
        fun formatText(text: String): String {
            return text.replace("&".toRegex(), "§")
        }
    }
}