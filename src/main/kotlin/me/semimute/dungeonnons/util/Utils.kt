package me.semimute.dungeonnons.util

import me.semimute.dungeonnons.Main
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer

class Utils: Main(){
    companion object {
        fun formatText(text: String): String {
            return text.replace("&".toRegex(), "§")
        }
    }
}