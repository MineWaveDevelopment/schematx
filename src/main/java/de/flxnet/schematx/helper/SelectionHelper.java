package de.flxnet.schematx.helper;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.selection.Selection;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SelectionHelper {

	public static ItemStack getSelectionTool() {
		Material material = SAccess.getConfig().getSelectionToolMaterial();
		
		ItemStack itemStack = new ItemStack(material, 1);
		ItemMeta itemMeta = (ItemMeta) itemStack.getItemMeta();
		itemMeta.setDisplayName(ConsoleHelper.prefix + "§6selection tool");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static String getLocationString(Location location) {
		return "[" + location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + "]";
	}
	
	public static String getChunkString(Chunk chunk) {
		return "[" + chunk.getWorld().getName() + ", " + chunk.getX() + ", " +chunk.getZ() + "]";
	}
	
	public static void hilightSelection(Selection selection) {
		if(selection.getFirstLocation() == null || selection.getSecondLocation() == null) return;
		World world = selection.getFirstLocation().getWorld();
		world.spawnParticle(Particle.REDSTONE, selection.getFirstLocation(), 5);
		world.spawnParticle(Particle.REDSTONE, selection.getSecondLocation(), 5);
	}
	
}
