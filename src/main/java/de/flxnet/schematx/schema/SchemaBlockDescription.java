package de.flxnet.schematx.schema;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.google.gson.annotations.Expose;

import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchemaBlockDescription {

	@Expose
	@Getter
	private int differenceX;
	
	@Expose
	@Getter
	private int differenceY;
	
	@Expose
	@Getter
	private int differenceZ;
	
	@Expose
	@Getter
	private String blockDataString;
	
	public SchemaBlockDescription(int differenceX, int differenceY, int differenceZ, String blockDataString) {
		this.differenceX = differenceX;
		this.differenceY = differenceY;
		this.differenceZ = differenceZ;
		this.blockDataString = blockDataString;
	}
	
	public SchemaBlockDescription(Block block, Location startLocation) {
		this.differenceX = block.getX() - startLocation.getBlockX();
		this.differenceY = block.getY() - startLocation.getBlockY();
		this.differenceZ = block.getZ() - startLocation.getBlockZ();
		this.blockDataString = block.getBlockData().getAsString();
	}
	
	@Override
	public String toString() {
		return differenceX + ";" + differenceY + ";" + differenceZ + ";" + blockDataString;
	}
	
	public static SchemaBlockDescription fromString(String data) {
		String[] split = data.split(";");
		if(split.length != 4) return null;
		return new SchemaBlockDescription(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), split[3]);
	}
	
}
