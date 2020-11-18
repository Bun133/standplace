package com.bun133.standplace.command

import com.bun133.standplace.Standplace
import com.bun133.standplace.util.RandomMaterialFactory
import com.bun133.standplace.util.SimpleMaterialFactory
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.random.Random

class StandCommand(var plugin: Standplace) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.isOp) {
                return run(sender, command, label, args)
            }
        } else {
            return run(sender, command, label, args)
        }
        return false
    }

    fun run(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            when (args[0]) {
                "start", "s" -> {
                    plugin.handler.map = hashMapOf()
                    plugin.server.onlinePlayers.filter { it.gameMode == GameMode.SURVIVAL }
                            .forEach { plugin.handler.map[it] = randomFactory() }
                    return true
                }

                "end", "e" -> {
                    plugin.handler.map = hashMapOf()
                    return true
                }

                "set" -> {
                    if (sender !is Player) {
                        sender.sendMessage("Please execute from Player!")
                        return true
                    } else if (args.size != 2) {
                        return false
                    } else {
                        val player: Player? = Bukkit.getPlayer(args[1])
                        if (player == null) {
                            sender.sendMessage("" + ChatColor.RED + "Player NotFound!")
                            return false
                        } else {
                            plugin.handler.map[player] = SimpleMaterialFactory(
                                    player.inventory.itemInMainHand.type
                            )
                        }
                    }
                }
            }
        }
        return false
    }

    @Deprecated("Please use RandomMaterialMaker")
    val materials: Array<Material> = arrayOf(
            Material.DIAMOND_BLOCK,
            Material.GOLD_BLOCK,
            Material.IRON_BLOCK,
            Material.CRAFTING_TABLE,
            Material.ANVIL,
            Material.CHEST,
            Material.END_PORTAL,
            Material.ACACIA_PLANKS,
            Material.OAK_PLANKS,
            Material.ACACIA_LOG,
            Material.OAK_LOG,
            Material.OBSIDIAN,
            Material.BEACON,
            Material.BEDROCK,
            Material.WHITE_BED
    )

    @Deprecated("Please Use #randomBlock")
    fun randomMaterial(): Material {
        return materials[Random.nextInt(materials.size)]
    }

    fun randomBlock(former: Block) {
        return plugin.random.factories[Random.nextInt(plugin.random.factories.size)].set(former)
    }

    val proxy: RandomProxy = RandomProxy(plugin)
    fun randomFactory(): RandomMaterialFactory {
        return proxy.get()
    }

    class RandomProxy(var plugin: Standplace){
        fun get():RandomMaterialFactory{
            val nonContained = plugin.random.factories
                    .filter { !plugin.handler.map.values.contains(it) }
            return if(nonContained.isEmpty()){
                plugin.random.factories[Random.nextInt(plugin.random.factories.size)]
            }else{
                nonContained[Random.nextInt(nonContained.size)]
            }
        }
    }
}