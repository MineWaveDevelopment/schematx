package de.flxnet.schematx.block;

import org.bukkit.block.data.BlockData;

import lombok.Getter;

/**
 * Software by FLXnet
 * More info at FLXnet.de Copyright (c) 2015-2020 by FLXnet
 * 
 * @author Felix
 */
public class PastedBlock {

	@Getter
	private int x;

	@Getter
	private int y;

	@Getter
	private int z;

	@Getter
	private BlockData blockData;

	public PastedBlock(int x, int y, int z, BlockData blockData) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockData = blockData;
	}

	/**
	 * public void setBlockFast(World world, int x, int y, int z, int blockId, byte
	 * data) { net.minecraft.server.v1_10_R1.World w = ((CraftWorld)
	 * world).getHandle(); Chunk chunk = w.getChunkAt(x >> 4, z >> 4); a(chunk, new
	 * BlockPosition(x, y, z),
	 * net.minecraft.server.v1_10_R1.Block.getById(blockId).fromLegacyData(data)); }
	 * 
	 * private IBlockData a(Chunk that, BlockPosition blockposition, IBlockData
	 * iblockdata) { return that.a(blockposition, iblockdata); }
	 **/

}
