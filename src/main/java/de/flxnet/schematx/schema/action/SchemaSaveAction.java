package de.flxnet.schematx.schema.action;

import org.bukkit.entity.Player;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.action.AsyncActions;
import de.flxnet.schematx.block.Cuboid;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.schema.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchemaSaveAction extends AbstractAction {
	
	@Getter @Setter
	private String name;
	
	public SchemaSaveAction(Player player, String name) {
		setPlayer(player);
		this.name = name;
	}
	
	@Override
	public void run() {
		
		Cuboid cuboid = SAccess.getSelectionManager().getCuboidSelection(getPlayer());
		
		if(cuboid == null) {
			ConsoleHelper.player(getPlayer(), "§6Please make a selection out of two points first");
			return;
		}
		
		Schema schema = new Schema(name, getPlayer().getUniqueId(), cuboid);
		
		AsyncActions.cloudSaveSchemaAction(getPlayer(), schema);
		
	}
	
}
