package com.bun133.standplace;

import org.bukkit.plugin.java.JavaPlugin;

public final class Standplace extends JavaPlugin {
    public StandHandler handler;
    public StandCommand command;
    @Override
    public void onEnable() {
        this.handler = new StandHandler();
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(handler,this);
        this.command = new StandCommand(this);
        this.getCommand("st").setExecutor(command);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
