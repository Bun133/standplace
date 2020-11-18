package com.bun133.standplace.command

import com.bun133.standplace.item.RandomItemFactory
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.isOp) {
                return run(sender, command, label, args)
            }
        } else {
            return run(sender, command, label, args)
        }
        sender.sendMessage("You don't have enough Perm!")
        return true
    }

    fun run(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when (args.size) {
            0 -> {
                if (sender is Player) {
                    sender.inventory.addItem(RandomItemFactory.getItem(1))
                }
            }
            1 -> {
                if (args[0].toIntOrNull() != null) {
                    if (sender is Player) {
                        sender.inventory.addItem(RandomItemFactory.getItem(args[0].toInt()))
                    }
                } else {
                    if (Bukkit.getOnlinePlayers().any { it.displayName === args[0] }) {
                        Bukkit.getOnlinePlayers().filter { it.displayName === args[0] }
                                .forEach { it.inventory.addItem(RandomItemFactory.getItem(1)) }
                    }
                }
            }
            2 -> {
                if (args[1].toIntOrNull() != null) {
                    if (Bukkit.getOnlinePlayers().any { it.displayName === args[0] }) {
                        Bukkit.getOnlinePlayers().filter { it.displayName === args[0] }
                                .forEach { it.inventory.addItem(RandomItemFactory.getItem(args[1].toInt())) }
                    }
                }
            }
        }
        return false
    }
}