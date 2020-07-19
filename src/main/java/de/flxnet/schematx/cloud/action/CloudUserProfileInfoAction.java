package de.flxnet.schematx.cloud.action;

import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.cloud.Cloud;
import de.flxnet.schematx.cloud.CloudRequest;
import de.flxnet.schematx.cloud.Pair;
import de.flxnet.schematx.helper.ConsoleHelper;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class CloudUserProfileInfoAction extends AbstractAction {

	public CloudUserProfileInfoAction(Player player) {
		setPlayer(player);
	}
	
	@Override
	public void run() {
		
		CloudRequest request = Cloud.getUserProfileInfo(getPlayer().getUniqueId());
		Pair<Integer, JsonObject> response = request.go();
		
		if(response == null) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve information about your SchematX Cloud profile");
			return;
		}
		
		JsonObject responseBody = response.getSecond();
		
		if(response.getFirst() != 200) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve information about your SchematX Cloud profile");
			ConsoleHelper.player(getPlayer(), "§6Response: §8" + responseBody);
			return;
		}
		
		String responseBodyCode = responseBody.get("code").getAsString();
		
		if(!responseBodyCode.equals("Ok")) {
			ConsoleHelper.playerError(getPlayer(), "Something went wrong, could not retrieve information about your SchematX Cloud profile");
			ConsoleHelper.player(getPlayer(), "§6Response-Body-Code: §8" + responseBodyCode);
			return;
		}
		
		JsonObject responseBodyMessage = responseBody.getAsJsonObject("message");
		
		ConsoleHelper.player(getPlayer(), "§bInformation about your SchematX Cloud profile");
		getPlayer().sendMessage("§2UUID: §7" + responseBodyMessage.get("uuid").getAsString());
		getPlayer().sendMessage("§2Created: §6" + responseBodyMessage.get("created").getAsLong());
		getPlayer().sendMessage("§2Quota: §d" + responseBodyMessage.get("quota").getAsInt() + " §8(§7max. amount of online saveable schemata§8)");
		
	}

}
