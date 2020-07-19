package de.flxnet.schematx.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.flxnet.schematx.schema.Schema;
import de.flxnet.schematx.schema.Visibility;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

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
	
	public static void displaySchema(Player player, Schema schema) {
		
		TextComponent message = new TextComponent();
		TextComponent dividerComponent = new TextComponent(" §7| ");
		
		// Name
		TextComponent nameComponent = new TextComponent(schema.getName() + " ");
		nameComponent.setColor(ChatColor.DARK_GREEN);
		LocalDateTime created = LocalDateTime.ofInstant(Instant.ofEpochMilli(schema.getCreated()), TimeZone.getDefault().toZoneId());
		nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("§2Created: §a" + created).create())));
		
		// Size
		TextComponent sizeComponent = new TextComponent(schema.getSize().toString());
		sizeComponent.setColor(ChatColor.GOLD);
		sizeComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("§bSize X, Y, Z §8(§e" + schema.getSize().getCubic() + " blocks§8)").create())));

		// Visibility
		TextComponent visibilityComponent;
		if (schema.getVisibility().equals(Visibility.Private)) {
			visibilityComponent = new TextComponent("§aPrivate");
			visibilityComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(new ComponentBuilder("§2This schema is §aPrivate§2, §2it can only be downloaded by §a§lyourself§2.").create())));
		} else {
			visibilityComponent = new TextComponent("§cPublic");
			visibilityComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(new ComponentBuilder("§2This schema is §cPublic§2, §2it can be downloaded by §c§leverybody§2.").create())));
		}

		// DownloadCounter
		int downloadCounter = schema.getDownloads();
		TextComponent downloadCounterComponent = new TextComponent("§f" + downloadCounter);
		downloadCounterComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(new ComponentBuilder("§2Schema has been downloaded §f" + downloadCounter + " §2times").create())));

		message.addExtra(nameComponent);
		message.addExtra(sizeComponent);
		message.addExtra(dividerComponent);
		message.addExtra(visibilityComponent);
		message.addExtra(dividerComponent);
		message.addExtra(downloadCounterComponent);
		
		player.spigot().sendMessage(message);
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
