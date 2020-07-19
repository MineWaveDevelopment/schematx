package de.flxnet.schematx.action;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.cloud.action.CloudListSchemataAction;
import de.flxnet.schematx.cloud.action.CloudLoadSchemaAction;
import de.flxnet.schematx.cloud.action.CloudSaveSchemaAction;
import de.flxnet.schematx.cloud.action.CloudUserProfileInfoAction;
import de.flxnet.schematx.schema.Schema;
import de.flxnet.schematx.schema.action.SchemaCreateAction;
import de.flxnet.schematx.schema.action.SchemaLoadAction;
import de.flxnet.schematx.schema.action.SchemaLoadFileAction;
import de.flxnet.schematx.schema.action.SchemaPasteAction;
import de.flxnet.schematx.schema.action.SchemaSaveAction;
import de.flxnet.schematx.schema.action.SchemaSaveFileAction;
import de.flxnet.schematx.selection.action.SelectCurrentChunkAction;
import de.flxnet.schematx.selection.action.SelectFirstLocationAction;
import de.flxnet.schematx.selection.action.SelectSecondLocationAction;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class AsyncActions {

	/*
	 * Selection
	 */
	public static void selectFirstLocationAction(Player player, Location location) {
		SAccess.getAsyncActionQueue().process(new SelectFirstLocationAction(player, location));
	}
	
	public static void selectSecondLocationAction(Player player, Location location) {
		SAccess.getAsyncActionQueue().process(new SelectSecondLocationAction(player, location));
	}
	
	public static void selectCurrentChunkAction(Player player) {
		SAccess.getAsyncActionQueue().process(new SelectCurrentChunkAction(player));
	}
	
	/*
	 * Save, load
	 */
	public static void schemaSaveFileAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaSaveFileAction(player, name));
	}
	
	public static void schemaLoadFileAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaLoadFileAction(player, name));
	}
	
	public static void schemaCreateAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaCreateAction(player, name));
	}
	
	public static void schemaLoadAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaLoadAction(player, name));
	}
	
	public static void schemaSaveAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new SchemaSaveAction(player, name));
	}
	
	public static void schemaPasteAction(Player player, Schema schema) {
		SAccess.getAsyncActionQueue().process(new SchemaPasteAction(player, schema));
	}
	
	/*
	 * Cloud
	 */
	public static void cloudUserProfileInfo(Player player) {
		SAccess.getAsyncActionQueue().process(new CloudUserProfileInfoAction(player));
	}
	
	public static void cloudListSchemata(Player player) {
		SAccess.getAsyncActionQueue().process(new CloudListSchemataAction(player));
	}
	
	public static void cloudLoadSchemaAction(Player player, String name) {
		SAccess.getAsyncActionQueue().process(new CloudLoadSchemaAction(player, name));
	}
	
	public static void cloudSaveSchemaAction(Player player, Schema schema) {
		SAccess.getAsyncActionQueue().process(new CloudSaveSchemaAction(player, schema));
	}
	
}
