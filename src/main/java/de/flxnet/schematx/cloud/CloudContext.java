package de.flxnet.schematx.cloud;

import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public enum CloudContext {

	UserProfileInfo("user"),
	ListSchemata("schemata"),
	GetSchema("schema"),
	SaveSchema("schema");
	
	@Getter
	private String url;
	
	private CloudContext(String url) {
		this.url = url;
	}
	
}
