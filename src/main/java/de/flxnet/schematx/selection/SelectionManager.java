package de.flxnet.schematx.selection;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import de.flxnet.schematx.block.Cuboid;
import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SelectionManager {

	@Getter
	private HashMap<UUID, Selection> selections;
	
	public SelectionManager() {
		this.selections = Maps.newHashMap();
	}
	
	public Cuboid getCuboidSelection(UUID uuid) {
		Selection selection = getSelection(uuid);
		if(!hasSelection(uuid)) return null;
		if(selection.getFirstLocation() == null || selection.getSecondLocation() == null) return null;
		return new Cuboid(selection.getFirstLocation(), selection.getSecondLocation());
	}
	
	public Cuboid getCuboidSelection(Player player) {
		return getCuboidSelection(player.getUniqueId());
	}
	
	public Selection getSelection(Player player) {
		return getSelection(player.getUniqueId());
	}
	
	public Selection getSelection(UUID uuid) {
		if(!hasSelection(uuid)) return null;
		return selections.get(uuid);
	}
	
	public boolean canSelect(UUID uuid) {
		if(!hasSelection(uuid)) return true;
		long lastSelection = getSelection(uuid).getLastSelectionAction();
		if(System.currentTimeMillis() > (lastSelection + (1000 * 1))) return true;
		return false;
	}
	
	public boolean hasSelection(UUID uuid) {
		return selections.containsKey(uuid);
	}
	
	public void selectFirstLocation(Player player, Location location) {
		if(!hasSelection(player.getUniqueId())) {
			selections.put(player.getUniqueId(), new Selection());
		}
		Selection selection = selections.get(player.getUniqueId());
		selection.setFirstLocation(location);
		selection.setLastSelectionAction(System.currentTimeMillis());
	}
	
	public void selectSecondLocation(Player player, Location location) {
		if(!hasSelection(player.getUniqueId())) {
			selections.put(player.getUniqueId(), new Selection());
		}
		Selection selection = selections.get(player.getUniqueId());
		selection.setSecondLocation(location);
		selection.setLastSelectionAction(System.currentTimeMillis());
	}
	
}
