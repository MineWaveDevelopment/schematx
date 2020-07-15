package de.flxnet.schematx.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AsyncActions;
import de.flxnet.schematx.block.Cuboid;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.CuboidHelper;
import de.flxnet.schematx.helper.SelectionHelper;
import de.flxnet.schematx.selection.Selection;
import net.minecraft.server.v1_16_R1.Particles;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchematxCommand implements CommandExecutor, TabCompleter {

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
		 * Load schema from file command
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("fileload")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("fileload")) {
			
			String name = args[1].trim();
			AsyncActions.schemaLoadFileAction(player, name);
			
			return true;
		}
		
		/*
		 * Save schema to file command
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("filesave")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("filesave")) {
			
			String name = args[1].trim();
			AsyncActions.schemaSaveFileAction(player, name);
			
			return true;
		}
		
		/*
		 * Set first location in selection, based on current player position
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("loc1")) {
			AsyncActions.selectFirstLocationAction(player, player.getLocation());
			return true;
		}
		
		/*
		 * Set second location in selection, based on current player position
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("loc2")) {
			AsyncActions.selectSecondLocationAction(player, player.getLocation());
			return true;
		}
		
		/*
		 * Test command for mark / hilight function
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("mark")) {
			
			Selection selection = SAccess.getSelectionManager().getSelection(player);
			Cuboid cuboid = new Cuboid(selection.getFirstLocation(), selection.getSecondLocation());
			CuboidHelper.markCuboid(player, cuboid, Particles.COMPOSTER);
			
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

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) return Lists.newArrayList("tool", "filesave", "fileload");
		return Collections.emptyList();
	}

}
