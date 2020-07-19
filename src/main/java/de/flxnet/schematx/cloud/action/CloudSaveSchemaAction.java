package de.flxnet.schematx.cloud.action;

import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.cloud.Cloud;
import de.flxnet.schematx.cloud.CloudRequest;
import de.flxnet.schematx.cloud.Pair;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.schema.Schema;
import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class CloudSaveSchemaAction extends AbstractAction {

	@Getter
	private Schema schema;
	
	public CloudSaveSchemaAction(Player player, Schema schema) {
		setPlayer(player);
		this.schema = schema;
	}
	
	@Override
	public void run() {
		
		CloudRequest request = Cloud.saveSchema(getPlayer().getUniqueId(), schema);
		Pair<Integer, JsonObject> response = request.go();
		
		if(response == null) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not save your new schema");
			return;
		}
		
		JsonObject responseBody = response.getSecond();
		
		if(response.getFirst() != 200) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not save your new schema");
			ConsoleHelper.player(getPlayer(), "§6Response: §8" + responseBody);
			return;
		}
		
		String responseBodyCode = responseBody.get("code").getAsString();
		
		if(responseBodyCode.equals("Ok")) {
			ConsoleHelper.player(getPlayer(), "§bSchema §a" + schema.getName() + " §bhas been saved");
			return;
		}
		
		if(responseBodyCode.equals("QuotaFull")) {
			ConsoleHelper.player(getPlayer(), "§6Could not save schema, there was no free space");
			return;
		}
		
		if(responseBodyCode.equals("Exists")) {
			ConsoleHelper.player(getPlayer(), "§6A schema named §b" + schema.getName() + " §6already exists in your storage");
			return;
		}
		
		ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not save your new schema");
		ConsoleHelper.player(getPlayer(), "§6Response-Body-Code: §8" + responseBodyCode);
		
	}

}
