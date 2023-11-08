package me.semimute.dungeonnons.events

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.semimute.dungeonnons.Main
import me.semimute.dungeonnons.core.Config
import me.semimute.dungeonnons.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.net.URL

@Serializable
data class NamesType(
        val NAMES: HashMap<String,String>
)

class ChatReceivedListener {

    fun fuckyou(inc: String): HashMap<String, String> {
        var mid = inc.removePrefix("{")
        mid = mid.removeSuffix("}")
        val midL = mid.split(',')
        val entries = midL.count()
        var i = 0
        var outg: HashMap<String, String> = hashMapOf<String, String>();
        while (i < entries) {
            val it = midL[i].split('=')
            outg.set(it[0], it[1])
            i++
        }
        return outg;
    }


    private val names =
        URL("https://gist.githubusercontent.com/SemiMute/dfb8b04e889ddffdd47291061e362f46/raw/").readText();

    private val nameObj = Json.decodeFromString<NamesType>(names);

    private val nameMap = fuckyou(nameObj.NAMES.toString());

    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        var replaceChat = false;
        var newText = event.message.unformattedText;
        var formattedText = event.message.formattedText.toString();
        if (Config.doBridgeBotFormatting) {
            var message = newText;
            if (message.startsWith("§2Guild >") && message.contains(Config.guildChatBridgeBotName)) {
                event.isCanceled = true;
                var messageSplit = message.split(" ");
                println(messageSplit);
                messageSplit = messageSplit.asSequence().drop(1).drop(1).drop(1).drop(1).drop(1).drop(1).toList();
                var username = messageSplit[0].replace("<", "").replace(">", "").replace(";", " ")
                println(username);
                messageSplit = messageSplit.drop(1);
                newText = "§dDiscord > §5$username§f: ${messageSplit.joinToString(" ")}";
            }
            if (message.startsWith("§9Party §8>") && Config.doPartyCommands && message.contains(Config.partyCommandPrefix)) {
                var messageSplit = message.split(" ");
                println(messageSplit)
                messageSplit = messageSplit.drop(1).drop(1);
                if (messageSplit[0].contains("[")) messageSplit = messageSplit.drop(1); // removes rank if they have one
                var username = messageSplit[0].replace(":", "").replace("§f", "");
                println(username)
                messageSplit = messageSplit.drop(1);
                var command = messageSplit[0].lowercase()
                if (!command.startsWith(Config.partyCommandPrefix)) return;
                command = command.replace(Config.partyCommandPrefix, "")

                // run command Logic
                when (command) {
                    "lme" -> {
                        if (!Config.cmdLeaderMe) return;
                        if (Minecraft.getMinecraft().thePlayer.name == username) return;
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText(Main.prefix + "§cTransfered party to " + username)))
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p transfer $username")
                    }

                    "summon" -> {
                        if (!Config.cmdSummon) return;
                        if (Minecraft.getMinecraft().thePlayer.name == username) return;
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText(Main.prefix + "§cSummoned Party due to request")))
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p warp")
                    }
                }
            }
        }
        if (Config.doCustomNames) {
            var newMessage = formattedText;

            nameMap.forEach {

                if (newMessage.contains(it.key.trim())) {
                    println("DETECTED CUSTOM NAME ATTEMPT" + "1111")
                    var splitMessage = newMessage.split(it.key.trim());
                    if (splitMessage[0].trim().last() == ']') {
                        val rankFind = "\\[[^\\]]*\\] ".toRegex().findAll(splitMessage[0]);
                        val rankVal = rankFind.lastOrNull();
                        if (rankVal != null) {
                            newMessage = newMessage.removeRange(rankVal.range);
                        }
                    }
                    val prevIt = it;
                    splitMessage = newMessage.split(it.key.trim());
                    var endMessage = "";
                    splitMessage.forEachIndexed { index, it ->
                        val colorMatch = "[§&][0-9a-fk-or]".toRegex().findAll(it);
                        val lastColorMatch = colorMatch.lastOrNull();
                        val color = lastColorMatch?.value ?: "§r"
                        endMessage += if (index < splitMessage.size - 1) {
                            it + prevIt.value.trim() + color
                        } else { it }
                        replaceChat = true;
                    }
                    newMessage = endMessage
                }
                newText = newMessage;
                println("text colorcoded")
            }
            if (replaceChat) {
                event.isCanceled = true;
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(newText))
            }
        }
    }
}