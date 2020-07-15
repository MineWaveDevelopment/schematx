package de.flxnet.schematx.action;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.schema.actions.SchemaLoadFileAction;
import de.flxnet.schematx.schema.actions.SchemaSaveFileAction;
import de.flxnet.schematx.selection.actions.SelectFirstLocationAction;
import de.flxnet.schematx.selection.actions.SelectSecondLocationAction;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class AsyncActions {

	public static void selectFirstLocationAction(Player player, Location location) {
		SAccess.getAsyncActionQueue().process(new SelectFirstLocationAction(player, location));
	}
	
	public static void selectSecondLocationAction(Player player, Location location) {
		SAccess.getAsyncActionQueue().process(new SelectSecondLocationAction(player, location));
	}
	
	public static void schemaSaveFileAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaSaveFileAction(player, name));
	}
	
	public static void schemaLoadFileAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaLoadFileAction(player, name));
	}
	
}
