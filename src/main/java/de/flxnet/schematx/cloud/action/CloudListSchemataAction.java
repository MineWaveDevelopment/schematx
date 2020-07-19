package de.flxnet.schematx.cloud.action;

import java.util.List;

import org.bukkit.entity.Player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.cloud.Cloud;
import de.flxnet.schematx.cloud.CloudRequest;
import de.flxnet.schematx.cloud.Pair;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.schema.Schema;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class CloudListSchemataAction extends AbstractAction {

	public CloudListSchemataAction(Player player) {
		setPlayer(player);
	}
	
	@Override
	public void run() {
		
		CloudRequest request = Cloud.listSchemata(getPlayer().getUniqueId());
		Pair<Integer, JsonObject> response = request.go();
		
		if(response == null) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve information about your stored schemata");
			return;
		}
		
		JsonObject responseBody = response.getSecond();
		
		if(response.getFirst() != 200) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve information about your stored schemata");
			ConsoleHelper.player(getPlayer(), "§6Response: §8" + responseBody);
			return;
		}
		
		String responseBodyCode = responseBody.get("code").getAsString();
		
		if(!responseBodyCode.equals("Ok")) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve information about your stored schemata");
			ConsoleHelper.player(getPlayer(), "§6Response-Body-Code: §8" + responseBodyCode);
			return;
		}
		
		JsonArray responseBodyMessage = responseBody.getAsJsonArray("message");
		
		if(responseBodyMessage.size() == 0) {
			ConsoleHelper.player(getPlayer(), "§6You have not saved any schemata in the cloud");
			return;
		}
		
		List<Schema> schemata = Cloud.fromJsonArray(responseBodyMessage);
		
		ConsoleHelper.player(getPlayer(), "§bYour cloud-saved schemata §8(§d" + schemata.size() + "§8)");
		for(Schema schema : schemata) {
			ConsoleHelper.displaySchema(getPlayer(), schema);
		}
		

		
	}

}
