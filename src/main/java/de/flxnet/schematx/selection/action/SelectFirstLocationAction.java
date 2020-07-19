package de.flxnet.schematx.selection.action;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.SelectionHelper;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SelectFirstLocationAction extends AbstractAction {

	@Getter @Setter
	private Location location;
	
	public SelectFirstLocationAction(Player player, Location location) {
		setPlayer(player);
		this.location = location;
	}
	
	@Override
	public void run() {
		SAccess.getSelectionManager().selectFirstLocation(getPlayer(), location);
		ConsoleHelper.player(getPlayer(), "§bSelected first location §6" + SelectionHelper.getLocationString(location));
	}

}
