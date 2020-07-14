package de.flxnet.schematx.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AsyncActions;
import de.flxnet.schematx.helper.SelectionHelper;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		Action action = e.getAction();
		
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		if(itemStack == null) return;
		
		Block clicked = e.getClickedBlock();
		if(clicked == null) return;
		
		Location clickedLocation = clicked.getLocation();
		
		if(!itemStack.equals(SelectionHelper.getSelectionTool())) return;
		if(e.getHand() != EquipmentSlot.HAND) return;
		
		e.setCancelled(true);
		
		if(!SAccess.getSelectionManager().canSelect(player.getUniqueId())) {
			System.out.println("cannot");
			return;
		}
		
		// Select FIRST location
		if(action == Action.LEFT_CLICK_BLOCK) {
			AsyncActions.selectFirstLocationAction(player, clickedLocation);
			return;
		}
		
		// Select SECOND location
		if(action == Action.RIGHT_CLICK_BLOCK) {
			AsyncActions.selectSecondLocationAction(player, clickedLocation);
			return;
		}
		
	}
	
}
