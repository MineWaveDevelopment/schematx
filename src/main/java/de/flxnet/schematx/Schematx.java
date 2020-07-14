package de.flxnet.schematx;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.flxnet.schematx.action.AsyncActionQueue;
import de.flxnet.schematx.action.SyncActionQueue;
import de.flxnet.schematx.command.SchematxCommand;
import de.flxnet.schematx.config.PluginConfig;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.listener.PlayerInteractListener;
import de.flxnet.schematx.selection.SelectionManager;
import lombok.Getter;

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
	private AsyncActionQueue asyncActionQueue;
	
	@Getter
	private SyncActionQueue syncActionQueue;
	
	@Getter
	private SelectionManager selectionManager;
	
	@Override
	public void onEnable() {
		instance = this;
		pluginConfig = new PluginConfig();
		
		asyncActionQueue = new AsyncActionQueue();
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, asyncActionQueue, 1, 1);
		
		syncActionQueue = new SyncActionQueue();
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, syncActionQueue, 1, 1);
		
		selectionManager = new SelectionManager();
		
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
	}
	
	private void setupEventListeners() {
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
	}
	
}
