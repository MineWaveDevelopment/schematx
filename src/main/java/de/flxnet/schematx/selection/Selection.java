package de.flxnet.schematx.selection;

import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class Selection {

	@Getter @Setter
	private Location firstLocation;
	
	@Getter @Setter
	private Location secondLocation;
	
	@Getter @Setter
	private long lastSelectionAction;
	
	public Selection() {}
	
}
