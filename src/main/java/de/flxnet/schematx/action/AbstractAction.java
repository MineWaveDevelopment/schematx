package de.flxnet.schematx.action;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public abstract class AbstractAction implements IAction {

	@Getter @Setter
	private Player player;
	
}
