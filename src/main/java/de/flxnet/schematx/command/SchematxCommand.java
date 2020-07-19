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

	private static List<String> subCommands = Lists.newArrayList("tool", "profile", "loc1", "loc2", "chunksel", "list", "save", "paste",
																	"loadlocal", "createlocal", "listlocal", "mark", "buffer");
	
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
		
		if(args.length >= 1) {
			String subCommand = args[0];
			if(!subCommands.contains(subCommand)) {
				ConsoleHelper.player(player, "§6Unknown subcommand §b[" + subCommand + "]");
				ConsoleHelper.player(player, "§bAvailable: §a" + Lists.newArrayList(subCommands));
				return true;
			}
		}
		
		////////////////
		// PRODUCTION //
		////////////////
		
		/*
		 * Create and save a schema to the cloud
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("save")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("save")) {
			String name = args[1].trim();
			AsyncActions.schemaSaveAction(player, name);
			return true;
		}
		
		/*
		 * Load and paste a cloud-saved schema
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("paste")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("paste")) {
			String name = args[1].trim();
			AsyncActions.cloudLoadSchemaAction(player, name);
			return true;
		}
		
		/*
		 * List a players cloud-saved schemata
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
			AsyncActions.cloudListSchemata(player);
			return true;
		}
		
		/*
		 * Retrieve information about a cloud-saved player profile
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("profile")) {
			AsyncActions.cloudUserProfileInfo(player);
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
		 * Command to give the SchematX selection tool to a player
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("tool")) {
			ItemStack itemStack = SelectionHelper.getSelectionTool();
			player.getInventory().addItem(itemStack);
			ConsoleHelper.player(player, "§bSchematX selection tool §7(" + itemStack.getType() + ") §bhas been added to your inventory.");
			return true;
		}
		
		/////////////////////
		// LOCAL FUNCTIONS //
		/////////////////////
		
		/*
		 * Load a schema
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("loadlocal")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("loadlocal")) {
			
			String name = args[1].trim();
			//AsyncActions.schemaLoadFileAction(player, name);
			AsyncActions.schemaLoadAction(player, name);
			
			return true;
		}
		
		/*
		 * Create a schema
		 */
		if(args.length == 1 && args[0].equalsIgnoreCase("createlocal")) {
			ConsoleHelper.player(player, "§6You are missing an argument §b[name]");
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("createlocal")) {
			
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
		
		/////////////
		// TESTING //
		/////////////
		
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
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) return subCommands;
		return Collections.emptyList();
	}

}
