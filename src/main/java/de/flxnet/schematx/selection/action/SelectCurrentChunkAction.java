package de.flxnet.schematx.selection.action;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.SelectionHelper;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SelectCurrentChunkAction extends AbstractAction {
	
	public SelectCurrentChunkAction(Player player) {
		setPlayer(player);
	}
	
	@Override
	public void run() {
		
		Chunk chunk = getPlayer().getLocation().getChunk();
		World world = chunk.getWorld();
		
		int startX = chunk.getX() << 4;
		int startY = 0;
		int startZ = chunk.getZ() << 4;
		
		System.out.println("x = " + startX + ", y = " + startY + " z = " + startZ);
		System.out.println("x = " + (startX + 15) + ", y = " + (startY + 255) + " z = " + (startZ + 15));
		
		Location chunkLower = new Location(world, startX, startY, startZ);
		Location chunkUpper = new Location(world, startX + 15, startY + 255, startZ + 15);
		
		SAccess.getSelectionManager().selectFirstLocation(getPlayer(), chunkLower);
		SAccess.getSelectionManager().selectSecondLocation(getPlayer(), chunkUpper);
		
		ConsoleHelper.player(getPlayer(), "§bSelected complete chunk §6" + SelectionHelper.getChunkString(chunk));
	}
	
}
