package me.semimute.dungeonnons.events

import me.semimute.dungeonnons.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Rat {
    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent){
        val message = event.message.unformattedText.toLowerCase();
        if((message.contains("rat ") or message.contains(" ratt")) and Config.ratEnabled){
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText("&c[RAT] Rat&f: &f*squeeks in french*&7 (stupid french rat)")))
            Minecraft.getMinecraft().thePlayer.playSound("mob.bat.idle", 1f, 0.5f);
        }
    }
}