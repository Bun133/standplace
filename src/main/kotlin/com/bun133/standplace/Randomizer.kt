package com.bun133.standplace

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class Randomizer(var plugin:Standplace):Listener{
    @EventHandler
    @Override
    fun onDeath(e:PlayerDeathEvent){
        plugin.handler.map[e.entity] = plugin.command.randomFactory()
    }
}