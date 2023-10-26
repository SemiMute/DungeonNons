package me.semimute.dungeonnons.commands

import me.semimute.dungeonnons.core.Config
import gg.essential.api.EssentialAPI
import gg.essential.api.commands.Command
import gg.essential.api.commands.DefaultHandler
import me.semimute.dungeonnons.Main
import me.semimute.dungeonnons.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object ConfigCommand : Command("dconfig") {
    @DefaultHandler
    fun handle() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText(Main.prefix + "&aOpening config menu...")))
        EssentialAPI.getGuiUtil().openScreen(Config.gui())
    }
}
