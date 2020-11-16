package com.bun133.standplace

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class StandHandler : Listener {
    var map:MutableMap<Player,Material> = hashMapOf()

    @Override
    fun onMove(e:PlayerMoveEvent){
        if(e.from.block != e.to!!.block){
            if(!e.to!!.add(0.0,-1.0,0.0).block.isEmpty
                    && !e.to!!.add(0.0,-1.0,0.0).block.isLiquid
                    && !e.to!!.add(0.0,-1.0,0.0).block.isPassable){
                if(map.containsKey(e.player)){
                    e.to!!.add(0.0,-1.0,0.0).block.type = map.getOrElse(e.player) { Material.AIR }
                }
            }
        }
    }
}