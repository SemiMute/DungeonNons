package me.semimute.dungeonnons.core

import me.semimute.dungeonnons.Main
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType

import java.io.File

object Config : Vigilant(File(Main.configLocation)) {

    @Property(
            type = PropertyType.SWITCH,
            name = "Pretty Guild Bot",
            description = "Makes the bridge bot incoming chats less.... ugly",
            category = "Guild"
    )
    var doBridgeBotFormatting = true

    @Property(
            type = PropertyType.TEXT,
            name = "Bridge Bot Username",
            description = "Enter your guilds bridge bot username",
            category = "Guild"
    )
    var guildChatBridgeBotName = "WeebchanBot"

    @Property(
            type = PropertyType.SWITCH,
            name = "Party Commands",
            description = "Allows party members to use commands to speed things up",
            category = "Party"
    )
    var doPartyCommands = false

    @Property(
            type = PropertyType.TEXT,
            name = "Party Command Prefix",
            description = "The character before the command name",
            category = "Party"
    )
    var partyCommandPrefix = "!"

    @Property(
            type = PropertyType.SWITCH,
            name = "Command: Leader Me",
            description = "Transfers to the executor: lme",
            category = "Party"
    )
    var cmdLeaderMe = true

    @Property(
            type = PropertyType.SWITCH,
            name = "Command: Summon",
            description = "Summons the party: summon",
            category = "Party"
    )
    var cmdSummon = true

    @Property(
            type = PropertyType.SWITCH,
            name = "Rat Interjections",
            description = "When a rat is mentioned, your personal rat will interject!",
            category = "Fun"
    )
    var doRatInterjection = true

    @Property(
            type = PropertyType.SWITCH,
            name = "Custom Names",
            description = "Parse SemiMute's Custom Names",
            category = "Fun"
    )
    var doCustomNames = true

    @Property(
            type = PropertyType.SWITCH,
            name = "Developer Mode",
            description = "Enables experimental or half baked changes. MAY BE UNSTABLE",
            category = "Fun"
    )
    var developerMode = false

    init {
        addDependency("guildChatBridgeBotName", "doBridgeBotFormatting")
        addDependency("partyCommandPrefix", "doPartyCommands")

        // commands
        addDependency("cmdLeaderMe", "doPartyCommands")
        addDependency("cmdSummon", "doPartyCommands")

        initialize()
    }
}
