package de.flxnet.schematx.cloud.action;

import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.action.AsyncActions;
import de.flxnet.schematx.cloud.Cloud;
import de.flxnet.schematx.cloud.CloudRequest;
import de.flxnet.schematx.cloud.Pair;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.JsonHelper;
import de.flxnet.schematx.schema.Schema;
import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class CloudLoadSchemaAction extends AbstractAction {

	@Getter
	private String name;
	
	public CloudLoadSchemaAction(Player player, String name) {
		setPlayer(player);
		this.name = name;
	}
	
	@Override
	public void run() {
		
		CloudRequest request = Cloud.getSchema(getPlayer().getUniqueId(), name);
		Pair<Integer, JsonObject> response = request.go();
		
		if(response == null) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve data of schema " + name);
			return;
		}
		
		JsonObject responseBody = response.getSecond();
		
		if(response.getFirst() != 200) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve data of schema " + name);
			ConsoleHelper.player(getPlayer(), "§6Response: §8" + responseBody);
			return;
		}
		
		String responseBodyCode = responseBody.get("code").getAsString();
		
		if(!responseBodyCode.equals("Ok")) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve data of schema " + name);
			ConsoleHelper.player(getPlayer(), "§6Response-Body-Code: §8" + responseBodyCode);
			return;
		}
		
		JsonObject responseBodyMessage = responseBody.getAsJsonObject("message");
		Schema schema = JsonHelper.getGson().fromJson(responseBodyMessage, Schema.class);
		
		if(schema == null) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not parse data of schema " + name);
			return;
		}
		
		AsyncActions.schemaPasteAction(getPlayer(), schema);
		
	}

}
