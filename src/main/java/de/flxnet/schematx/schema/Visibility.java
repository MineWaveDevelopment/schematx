package de.flxnet.schematx.schema;

import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public enum Visibility {

	Private("§cPrivate"),
	Public("§aPublic");
	
	@Getter
	String coloredString;
	
	private Visibility(String coloredString) {
		this.coloredString = coloredString;
	}
	
}
