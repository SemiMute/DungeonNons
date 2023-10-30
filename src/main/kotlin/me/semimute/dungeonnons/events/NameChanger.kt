package me.semimute.dungeonnons.events
import me.semimute.dungeonnons.core.Config
import me.semimute.dungeonnons.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.net.URL
import kotlinx.serialization.Serializable



// im p sure I need an object with 2 properties, but how tf do I serialize that??
//thank you intellij for marking my non-capital Is in the comments the same way as errors... fuckin scared the shit out of me
//had to change out ta to out of because it's "informal" and that's bad enough for a red error underline, which I'm not having!
//I want to die
@Serializable
data class NamesType(
    //just realized I read the Json wrong hahahahahahahahahahahahaahahahahahahahahahahaha
    //I'm trying a hashmap which should not be used in this case but I can't think of anything better, this is going to go terribly wrong I can feel it
    val NAMES: HashMap<String,String>
)

class NameChanger {


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

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent){
        val message = event.message.unformattedText.toString();
        val Names = URL("https://gist.githubusercontent.com/SemiMute/dfb8b04e889ddffdd47291061e362f46/raw/").readText();

        val nameObj = Json.decodeFromString<NamesType>(Names);

        val nameMap = fuckyou(nameObj.NAMES.toString());
        //KILLLLLLL MEEEEEEE
        var newMessage = message;
        nameMap.forEach {

            if (newMessage.contains(it.key.trim())) {
                val lastColorCheckString = newMessage.split(it.key)[0];
                val colorMatch = "&[0-9a-fk-or]".toRegex().findAll(lastColorCheckString);
                val lastColorMatch = colorMatch.lastOrNull();
                val color: String;
                if (lastColorMatch != null){
                    color = lastColorMatch.value;
                }
                else{
                    color = "&r"
                } // I CAN KILL GOD
                newMessage = newMessage.replace(it.key.trim(), it.value.trim() + color)
                event.isCanceled = true;
            }
        //omg I think I did something
        }

        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(Utils.formatText(newMessage)))

        //why am I doing this to myself
        //It's semimutes fault
        //I'm coming for you

    }
}
