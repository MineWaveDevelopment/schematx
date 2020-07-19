package de.flxnet.schematx.cloud;

import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public enum Method {

	Get("GET"),
	Post("POST");
	
	@Getter
	private String method;
	
	private Method(String method) {
		this.method = method;
	}
	
}
