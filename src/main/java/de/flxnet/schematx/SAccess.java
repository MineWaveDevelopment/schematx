package de.flxnet.schematx;

import de.flxnet.schematx.action.AbstractActionQueue;
import de.flxnet.schematx.config.PluginConfig;
import de.flxnet.schematx.schema.SchemaManager;
import de.flxnet.schematx.selection.SelectionManager;
import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SAccess {

	@Getter
	private static Schematx instance = Schematx.getInstance();
	
	@Getter
	private static PluginConfig config = instance.getPluginConfig();
	
	@Getter
	private static AbstractActionQueue asyncActionQueue = instance.getAsyncActionQueue();
	
	@Getter
	private static AbstractActionQueue syncActionQueue = instance.getSyncActionQueue();
	
	@Getter
	private static SelectionManager selectionManager = instance.getSelectionManager();
	
	@Getter
	private static SchemaManager schemaManager = instance.getSchemaManager();
	
}
