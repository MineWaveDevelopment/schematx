package de.flxnet.schematx.command;

import java.io.File;
import java.util.Arrays;
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
import de.flxnet.schematx.helper.PersistenceHelper;
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
		 * Load a schema
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("load")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("load")) {
			
			String name = args[1].trim();
			//AsyncActions.schemaLoadFileAction(player, name);
			AsyncActions.schemaLoadAction(player, name);
			
			return true;
		}
		
		/*
		 * Create a schema
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("create")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("create")) {
			
			String name = args[1].trim();
			//AsyncActions.schemaSaveFileAction(player, name);
			AsyncActions.schemaCreateAction(player, name);
			
			return true;
		}
		
		/*
		 * List local saved schemata
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("listlocal")) {
			
			File schemataFolder = PersistenceHelper.getSchemaFolder();
			
			Arrays.stream(schemataFolder.listFiles()).sorted().forEach(file -> {
				
				player.sendMessage("§a" + file.getName() + " §d" + file.length() + "b");
				
			});
			
			return true;
		}
		
		/*
		 * Select the complete chunk (16x16x256) currently standing on
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("chunksel")) {
			AsyncActions.selectCurrentChunkAction(player);
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
		 * Show amount of schemata in the buffer
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("buffer")) {
			
			int bufferSize = SAccess.getSchemaManager().getBufferSize();
			if(bufferSize == 0) {
				ConsoleHelper.player(player, "§6The schema buffer is currently empty");
				return true;
			}
			
			ConsoleHelper.player(player, "§6There are currently §d" + bufferSize + " §6schemata in the buffer");
			
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
		if(args.length == 1) return Lists.newArrayList("tool", "create", "load", "loc1", "loc2", "chunksel", "buffer", "listlocal");
		return Collections.emptyList();
	}

}
