package de.flxnet.schematx.block;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.flxnet.schematx.Schematx;
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
	private String blockDataString;

	public PastedBlock(int x, int y, int z, String blockDataString) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockDataString = blockDataString;
	}

	public static class BlockQueue {

		private Deque<PastedBlock> queue = new ConcurrentLinkedDeque<>();
		private static Map<World, BlockQueue> queueMap = new ConcurrentHashMap<>();

		public void add(PastedBlock block) {
			queue.add(block);
		}

		public BlockQueue(final World world) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Schematx.getInstance(), () -> {
				PastedBlock block = null;
				boolean hasTime = true;
				long start = System.currentTimeMillis();

				while ((block = queue.poll()) != null && hasTime) {

					hasTime = System.currentTimeMillis() - start < 10;
					world.getBlockAt(block.x, block.y, block.z)
							.setBlockData(Bukkit.createBlockData(block.blockDataString));
				}
			}, 1, 1);
		}

		public static BlockQueue getQueue(World w) {
			if (!queueMap.containsKey(w)) {
				BlockQueue blockQueue = new BlockQueue(w);
				queueMap.put(w, blockQueue);

				return blockQueue;
			}
			return queueMap.get(w);
		}
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
