package com.bun133.standplace;

import com.bun133.standplace.command.GiveCommand;
import com.bun133.standplace.command.StandCommand;
import com.bun133.standplace.item.RandomItemDistributor;
import com.bun133.standplace.item.RandomItemListener;
import com.bun133.standplace.util.RandomMaterialMaker;
import com.bun133.standplace.util.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Standplace extends JavaPlugin {
    public StandHandler handler;
    public StandCommand command;
    public Randomizer randomizer;
    public RandomItemListener item_Listener;
    public RandomMaterialMaker random;
    public Scheduler scheduler;
    public RandomItemDistributor distributor;
    public GiveCommand giveCommand;
    @Override
    public void onEnable() {
        this.scheduler = new Scheduler(this);
        this.handler = new StandHandler();
        this.randomizer = new Randomizer(this);
        this.item_Listener = new RandomItemListener(this);
        this.random = new RandomMaterialMaker(this);
        this.distributor = new RandomItemDistributor(this);
        this.giveCommand = new GiveCommand();
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(handler,this);
        this.getServer().getPluginManager().registerEvents(randomizer,this);
        this.getServer().getPluginManager().registerEvents(item_Listener,this);
        this.command = new StandCommand(this);
        this.getCommand("st").setExecutor(command);
        this.getCommand("sg").setExecutor(giveCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
