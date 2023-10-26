package me.semimute.dungeonnons.util

import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class CustomName {
    @SubscribeEvent
    fun onChatEvent(event: ClientChatReceivedEvent){
        event.message.unformattedText
    }
}