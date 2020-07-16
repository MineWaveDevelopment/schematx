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
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SyncBlockQueue {

	@Getter
	private static Map<World, SyncBlockQueue> queueMap = new ConcurrentHashMap<>();
	
	@Getter
	private Deque<PastedBlock> queue;

	public SyncBlockQueue(final World world) {
		queue = new ConcurrentLinkedDeque<>();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Schematx.getInstance(), () -> {
			
			PastedBlock block = null;
			boolean hasTime = true;
			long start = System.currentTimeMillis();

			while ((block = queue.poll()) != null && hasTime) {

				hasTime = System.currentTimeMillis() - start < 10;
				world.getBlockAt(block.getX(), block.getY(), block.getZ()).setBlockData(block.getBlockData());
			}
			
		}, 1, 1);
		
		System.out.println("[SyncBlockQueue] [" + world.getName() + "] has been created!");
	}
	
	public void add(PastedBlock block) {
		queue.add(block);
	}

	public static SyncBlockQueue getQueue(World w) {
		if (!queueMap.containsKey(w)) {
			SyncBlockQueue blockQueue = new SyncBlockQueue(w);
			queueMap.put(w, blockQueue);
			return blockQueue;
		}
		return queueMap.get(w);
	}
	
}
