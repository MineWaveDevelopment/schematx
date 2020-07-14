package de.flxnet.schematx.helper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class ConsoleHelper {

	public static String prefix = "§f[§aSchemat§bX§f] §r";
	
	public static void player(Player player, String message) {
		player.sendMessage(prefix + message);
	}
	
	/**
	 * 
	 * @param player
	 * @param message
	 */
	public static void playerSuccess(Player player, String message) {
		player(player, "§a" + message);
	}
	
	/**
	 * 
	 * @param player
	 * @param message
	 */
	public static void playerWarning(Player player, String message) {
		player(player, "§6" + message);
	}
	
	/**
	 * 
	 * @param player
	 * @param message
	 */
	public static void playerError(Player player, String message) {
		player(player, "§4Error: §c" + message);
	}
	
	/**
	 * 
	 * @param player
	 * @param message
	 */
	public static void playerInfo(Player player, String message) {
		player(player, "§3" + message);
	}
	
	/**
	 * 
	 * @param player
	 */
	public static void playerNoPermission(Player player) {
		player(player, "§cYou do not have the permission to use this command.");
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void console(String message) {
		Bukkit.getConsoleSender().sendMessage(prefix + message);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void consoleSuccess(String message) {
		console("§a" + message);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void consoleWarning(String message) {
		console("§6" + message);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void consoleError(String message) {
		console("§4Error: §c" + message);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void consoleInfo(String message) {
		console("§3" + message);
	}
	
}
