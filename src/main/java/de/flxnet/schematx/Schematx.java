package de.flxnet.schematx;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.flxnet.schematx.action.AbstractActionQueue;
import de.flxnet.schematx.command.SchematxCommand;
import de.flxnet.schematx.config.PluginConfig;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.LagMeter;
import de.flxnet.schematx.listener.PlayerInteractListener;
import de.flxnet.schematx.schema.SchemaManager;
import de.flxnet.schematx.selection.SelectionManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class Schematx extends JavaPlugin {

	@Getter
	private static Schematx instance;
	
	@Getter
	private PluginConfig pluginConfig;
	
	@Getter
	private AbstractActionQueue asyncActionQueue;
	
	@Getter
	private AbstractActionQueue syncActionQueue;
	
	@Getter
	private SelectionManager selectionManager;
	
	@Getter
	private SchemaManager schemaManager;
	
	@Override
	public void onEnable() {
		instance = this;
		pluginConfig = new PluginConfig();
		
		Bukkit.getScheduler().runTaskTimer(this, new LagMeter(), 100L, 1L);
		initLagMeterDisplay();
		
		asyncActionQueue = new AbstractActionQueue("AsyncActionQueue", true);
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, asyncActionQueue, 1, 1);
		
		syncActionQueue = new AbstractActionQueue("SyncActionQueue", true);
		Bukkit.getScheduler().runTaskTimer(this, syncActionQueue, 1, 1);
		
		selectionManager = new SelectionManager();
		schemaManager = new SchemaManager(true);
		
		setupCommands();
		setupEventListeners();
		
		ConsoleHelper.console("§3Plugin enabled!");
	}
	
	@Override
	public void onDisable() {
		ConsoleHelper.console("§3Plugin disabled!");
	}
	
	private void setupCommands() {
		getCommand("schematx").setExecutor(new SchematxCommand());
		getCommand("schematx").setTabCompleter(new SchematxCommand());
	}
	
	private void setupEventListeners() {
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
	}
	
	private void initLagMeterDisplay() {
		Bukkit.getScheduler().runTaskTimer(Schematx.getInstance(), task -> {
			Bukkit.getOnlinePlayers().forEach(player -> {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6TPS §2" + LagMeter.getTpsString()));
			});
		}, 20 * 1, 20 * 1);
	}
	
}
