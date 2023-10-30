package me.semimute.dungeonnons

import com.google.gson.*
import me.semimute.dungeonnons.commands.ConfigCommand
import me.semimute.dungeonnons.core.Config
import me.semimute.dungeonnons.events.ChatReceivedListener
import me.semimute.dungeonnons.events.NameChanger
import me.semimute.dungeonnons.events.Rat
import me.semimute.dungeonnons.events.packet.PacketListener
import me.semimute.dungeonnons.util.Utils
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import scala.util.parsing.json.JSONArray
import scala.util.parsing.json.`JSONArray$`
import scala.util.parsing.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import java.util.function.Consumer

@Mod(
    modid = Main.MOD_ID,
    name = Main.MOD_NAME,
    version = Main.VERSION
)
open class Main {
    companion object {
        const val MOD_ID = "dungeonnons"
        const val MOD_NAME = "DungeonNons"
        const val VERSION = "1.0"
        const val configLocation = "./config/examplemod.toml"
        val prefix = Utils.formatText("&c&lDungeonNons &8>&f ")
    }
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        Config.preload()

        ConfigCommand.register()

        MinecraftForge.EVENT_BUS.register(ChatReceivedListener())
        MinecraftForge.EVENT_BUS.register(Rat())
        MinecraftForge.EVENT_BUS.register(PacketListener())
        MinecraftForge.EVENT_BUS.register(NameChanger())

    }
}
