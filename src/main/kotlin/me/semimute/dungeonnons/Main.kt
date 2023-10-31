package me.semimute.dungeonnons

import me.semimute.dungeonnons.commands.ConfigCommand
import me.semimute.dungeonnons.core.Config
import me.semimute.dungeonnons.events.ChatReceivedListener
import me.semimute.dungeonnons.events.Rat
import me.semimute.dungeonnons.events.packet.PacketListener
import me.semimute.dungeonnons.util.Utils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

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
        MinecraftForge.EVENT_BUS.register(PacketListener());
    }
}
