package de.flxnet.schematx.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.SelectionHelper;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchematxCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ConsoleHelper.prefix + "§cThis command is only available for players.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 0) {
			
			return true;
		}
		
		/*
		 * Command to give the SchematX selection tool to a player
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("tool")) {
			ItemStack itemStack = SelectionHelper.getSelectionTool();
			player.getInventory().addItem(itemStack);
			ConsoleHelper.player(player, "§bSchematX selection tool §7(" + itemStack.getType() + ") §bhas been added to your inventory.");
			return true;
		}
		
		return true;
	}

}
