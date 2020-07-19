package de.flxnet.schematx.cloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.JsonObject;

import de.flxnet.schematx.helper.JsonHelper;
import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class CloudRequest {

	@Getter
	private CloudContext context;
	
	@Getter
	private Method method;
	
	@Getter
	private List<Pair<String, String>> parameters;
	
	@Getter
	private String body;
	
	public CloudRequest(CloudContext context, Method method, List<Pair<String, String>> parameters, String body) {
		this.context = context;
		this.method = method;
		this.parameters = parameters;
		this.body = body;
	}
	
	public CloudRequest(CloudContext context, Method method, List<Pair<String, String>> parameters) {
		this(context, method, parameters, null);
	}
	
	public Pair<Integer, JsonObject> go() {
		try {
			
			URL url = new URL(buildUrl());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.getMethod());
			
			if(body != null) {
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				connection.setRequestProperty("Accept", "application/json");
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(body);
				writer.flush();
				writer.close();
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder builder = new StringBuilder();
			while ((inputLine = reader.readLine()) != null) {
				builder.append(inputLine);
			}
			reader.close();
			
			JsonObject object = JsonHelper.getGson().fromJson(builder.toString(), JsonObject.class);
			if(object == null) return Pair.of(connection.getResponseCode(), new JsonObject());
			
			return Pair.of(connection.getResponseCode(), object);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String buildUrl() {
		String basicUrl = Cloud.CLOUD_URL + context.getUrl();
		if(parameters.isEmpty()) return basicUrl;
		
		StringBuilder builder = new StringBuilder();
		builder.append(basicUrl);
		for(int i = 0; i < parameters.size(); i++) {
			Pair<String, String> parameter = parameters.get(i);
			if(i == 0) {
				builder.append("?" + parameter.getFirst() + "=" + parameter.getSecond());
			} else {
				builder.append("&" + parameter.getFirst() + "=" + parameter.getSecond());
			}
		}
		
		return builder.toString().trim();
	}
	
}
