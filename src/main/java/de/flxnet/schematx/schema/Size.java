package de.flxnet.schematx.schema;

import com.google.gson.annotations.Expose;

import de.flxnet.schematx.block.Cuboid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
@AllArgsConstructor
public class Size {

	@Expose
	@Getter @Setter
	private int sizeX;
	
	@Expose
	@Getter @Setter
	private int sizeY;
	
	@Expose
	@Getter @Setter
	private int sizeZ;
	
	public static Size fromCuboid(Cuboid cuboid) {
		return new Size(cuboid.getSizeX(), cuboid.getSizeY(), cuboid.getSizeZ());
	}
	
	@Override
	public String toString() {
		return "[" + sizeX + "x" + sizeY + "x" + sizeZ + "]";
	}
	
	public int getCubic() {
		return sizeX * sizeY * sizeZ;
	}
	
}
