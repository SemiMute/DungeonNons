package me.semimute.dungeonnons.events

import me.semimute.dungeonnons.Main
import me.semimute.dungeonnons.core.Config
import me.semimute.dungeonnons.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import scala.annotation.switch
import java.util.*

class ChatReceivedListener {
    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent){
        if(Config.doBridgeBotFormatting){
            var player = Minecraft.getMinecraft().thePlayer;
            var message = event.message.unformattedText;
            if(message.startsWith("§2Guild >") && message.contains(Config.guildChatBridgeBotName)){
                event.isCanceled = true;
                var messageSplit = message.split(" ");
                println(messageSplit);
                messageSplit = messageSplit.asSequence().drop(1).drop(1).drop(1).drop(1).drop(1).toList();
                var username = messageSplit[0].replace("<", "").replace(">", "").replace(";", " ")
                println(username);
                messageSplit = messageSplit.drop(1);
                player.addChatMessage(ChatComponentText(Utils.formatText("&dDiscord > &5$username&f: ${messageSplit.joinToString(" ")}")))
            } else if(message.startsWith("§9Party §8>") && Config.doPartyCommands){
                var messageSplit = message.split(" ");
                println(messageSplit)
                messageSplit = messageSplit.drop(1).drop(1);
                if(messageSplit[0].contains("[")) messageSplit = messageSplit.drop(1); // removes rank if they have one
                var username = messageSplit[0].replace(":", "").replace("§f", "");
                println(username)
                messageSplit = messageSplit.drop(1);
                var command = messageSplit[0].lowercase()
                if(!command.startsWith(Config.partyCommandPrefix)) return;
                command = command.replace(Config.partyCommandPrefix, "")

                // run command Logic
                when(command){
                     "test" -> {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/is")
                    }
                    "lme" -> {
                        if(!Config.cmdLeaderMe) return;
                        if(Minecraft.getMinecraft().thePlayer.name == username) return;
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText(Main.prefix + "&cTransfered party to " + username)))
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p transfer $username")
                    }
                    "summon" -> {
                        if(!Config.cmdSummon) return;
                        if(Minecraft.getMinecraft().thePlayer.name == username) return;
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText(Main.prefix + "&cSummoned Party due to request")))
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p warp")
                    }
                }


            }
        }
    }
}