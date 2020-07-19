package de.flxnet.schematx.cloud;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.helper.JsonHelper;
import de.flxnet.schematx.schema.Schema;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class Cloud {

	public static String CLOUD_URL = SAccess.getConfig().getCloudUrl();
	
	public static CloudRequest getUserProfileInfo(UUID user) {
		return new CloudRequest(CloudContext.UserProfileInfo, Method.Get, Lists.newArrayList(Pair.of("uuid", user.toString())));
	}
	
	public static CloudRequest listSchemata(UUID user) {
		return new CloudRequest(CloudContext.ListSchemata, Method.Get, Lists.newArrayList(Pair.of("owner", user.toString())));
	}
	
	public static CloudRequest getSchema(UUID user, String name) {
		return new CloudRequest(CloudContext.GetSchema, Method.Get, Lists.newArrayList(Pair.of("owner", user.toString()), Pair.of("name", name)));
	}
	
	public static CloudRequest saveSchema(UUID user, Schema schema) {
		return new CloudRequest(CloudContext.SaveSchema, Method.Post, Lists.newArrayList(Pair.of("uuid", user.toString())), schema.getJsonData());
	}
	
	public static List<Schema> fromJsonArray(JsonArray jsonArray) {
		List<Schema> schemata = Lists.newArrayList();
		for(JsonElement element : jsonArray) {
			schemata.add(JsonHelper.getGson().fromJson(element, Schema.class));
		}
		return schemata;
	}
	
}
