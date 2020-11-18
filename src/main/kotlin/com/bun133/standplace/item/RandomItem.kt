package com.bun133.standplace.item

import com.bun133.standplace.Standplace
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

val ResetItemName: String = "リセマラ"
val lore: List<String> = listOf("このアイテムを使うと自分の能力をリセマラできるよ！")

class RandomItemFactory {
    companion object {
        fun getItem(amount:Int): ItemStack {
            val item: ItemStack = ItemStack(Material.SNOWBALL, amount)
            val meta = item.itemMeta
            meta!!.setDisplayName(ResetItemName)
            meta.setLocalizedName(ResetItemName)
            meta.lore = lore
            meta.addEnchant(Enchantment.DAMAGE_ALL,1,true)
            return item
        }

        fun isResetItem(stack: ItemStack): Boolean {
            if (stack.type != Material.SNOWBALL) return false
            if (stack.itemMeta!!.displayName === ResetItemName) {
                return true
            }
            return false
        }
    }
}

class RandomItemListener(var plugin: Standplace) : Listener {
    @Override
    @EventHandler
    fun onPlayerSnowBallThrow(e: ProjectileLaunchEvent) {
        if (e.entityType === EntityType.SNOWBALL) {
            if (e.entity is Snowball) {
                if (e.entity.shooter is Player) {
                    val snowball: Snowball = e.entity as Snowball
                    val player: Player = e.entity.shooter as Player

                    if (RandomItemFactory.isResetItem(snowball.item)) {
                        player.sendMessage("あなたの能力はランダムに変更されました！")
                        snowball.item.amount -= 1
                        plugin.handler.map[player] = plugin.command.randomFactory()
                    }
                }
            }
        }
    }
}

class RandomItemDistributor(var plugin: Standplace): BukkitRunnable() {
    init {
        // Each Min
        this.runTaskTimer(plugin,20*60,20*60)
    }

    override fun run() {
        plugin.handler.map.forEach { map -> map.key.inventory.addItem(RandomItemFactory.getItem(1)) }
    }
}