package com.bun133.standplace.item

import com.bun133.standplace.Standplace
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

val ResetItemName: String = "リセマラ"
val lore: List<String> = listOf("このアイテムを使うと自分の能力をリセマラできるよ！")

class RandomItemFactory {
    companion object {
        fun getItem(amount: Int): ItemStack {
            val item = ItemStack(Material.SNOWBALL, amount)
            val meta = item.itemMeta
            meta!!.setDisplayName(ResetItemName)
            meta.setLocalizedName(ResetItemName)
            meta.lore = lore
            item.itemMeta = meta
            return item
        }

        fun isResetItem(stack: ItemStack): Boolean {
//            println("IsResetItem")
//            if (stack.type != Material.SNOWBALL) {
//                println("Stack Is not SnowBall")
//                return false
//            }
//            if (stack.itemMeta!!.displayName === ResetItemName) {
//                return true
//            } else println("Name Was:${stack.itemMeta!!.displayName}")
//            return false
            return getItem(stack.amount).isSimilar(stack)
        }
    }
}

class RandomItemListener(var plugin: Standplace) : Listener {
//    @Override
//    @EventHandler
//    fun onPlayerSnowBallThrow(e: ProjectileLaunchEvent) {
//        plugin.scheduler.setTimer(1L, Runnable {
//            if (e.entityType == EntityType.SNOWBALL) {
//                if (e.entity is Snowball) {
//                    if (e.entity.shooter is Player) {
//                        val snowball: ThrowableProjectile = e.entity as ThrowableProjectile
//                        val player: Player = e.entity.shooter as Player
//
//                        if (RandomItemFactory.isResetItem(snowball.item)) {
//                            player.sendMessage("あなたの能力はランダムに変更されました！")
//                            snowball.item.amount -= 1
//                            plugin.handler.map[player] = plugin.command.randomFactory()
//                        } else {
//                            println("The SnowBall is nearly Random Item,but it wasn't")
//                        }
//                    }
//                } else println("Enitity Type is SnowBall but,instance isn't")
//            }
//        })
//    }

    @Override
    @EventHandler
    fun onPlayerUseItem(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            println("ItemUsed")
            //Right Click Item
            if (e.item != null) {
                if (RandomItemFactory.isResetItem(e.item!!)) {
                    e.player.sendMessage("あなたの能力はランダムに変更されました！")
                    e.item!!.amount -= 1
                    plugin.handler.map[e.player] = plugin.command.randomFactory()
                } else {
                    println("Item is not RandomItem!")
                    println("AAA")
                }
            } else println("Item is Null!")
        }
    }
}

class RandomItemDistributor(var plugin: Standplace) : BukkitRunnable() {
    init {
        // Each Min
        this.runTaskTimer(plugin, 20 * 60, 20 * 60)
    }

    override fun run() {
        plugin.handler.map.forEach { map -> map.key.inventory.addItem(RandomItemFactory.getItem(1)) }
    }
}