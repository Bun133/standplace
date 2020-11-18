package com.bun133.standplace

import com.bun133.standplace.util.RandomMaterialFactory
import com.bun133.standplace.util.SimpleMaterialFactory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class StandHandler : Listener {
    var map: MutableMap<Player, RandomMaterialFactory> = hashMapOf()

    @Override
    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        if (e.from.block != e.to!!.block) {
            if (!e.to!!.block.location.add(0.0, -1.0, 0.0).block.isEmpty
                    && !e.to!!.block.location.add(0.0, -1.0, 0.0).block.isLiquid
                    && !e.to!!.block.location.add(0.0, -1.0, 0.0).block.isPassable) {
                if (map.containsKey(e.player)) {
                    map.getOrElse(e.player) { SimpleMaterialFactory(Material.AIR) }.set(e.to!!.block.location.add(0.0, -1.0, 0.0).block)
                }
            }
        }
    }
}