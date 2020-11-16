package com.bun133.standplace

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.random.Random

class StandCommand(var plugin:Standplace) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            if(sender.isOp){
                return run(sender, command, label, args)
            }
        }else{
            return run(sender, command, label, args)
        }
        return false
    }

    fun run(sender: CommandSender, command: Command, label: String, args: Array<out String>):Boolean{
        if(args.isNotEmpty()){
            when(args[0]){
                "start","s" -> {
                    plugin.handler.map = hashMapOf()
                    plugin.server.onlinePlayers.filter { it.gameMode == GameMode.SURVIVAL }
                            .forEach { plugin.handler.map[it] = randomMaterial()}
                    return true
                }

                "end","e" ->{
                    plugin.handler.map = hashMapOf()
                    return true
                }
            }
        }
        return false
    }

    val materials:Array<Material> = arrayOf(
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

    fun randomMaterial():Material{
        return materials[Random.nextInt(materials.size)]
    }
}