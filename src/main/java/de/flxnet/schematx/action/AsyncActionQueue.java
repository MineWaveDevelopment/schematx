package de.flxnet.schematx.action;

import java.util.Queue;

import com.google.common.collect.Queues;

import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class AsyncActionQueue implements Runnable {

	@Getter
	private Queue<AbstractAction> actions;
	
	@Getter
	private long lastMillis = 0;
	
	@Getter @Setter
	private int interval = 10;
	
	public AsyncActionQueue() {
		this.actions = Queues.newConcurrentLinkedQueue();
	}
	
	public void process(AbstractAction action) {
		this.actions.add(action);
	}

	@Override
	public void run() {
		long currentMillis = System.currentTimeMillis();
		
		if((currentMillis - lastMillis) >= interval) {
			lastMillis = currentMillis;
			
			if(actions.size() == 0) return;
			
			IAction action = this.actions.poll();
			if(action == null) return;
			
			action.run();
			System.out.println("[AsyncActionQueue] Running " + action.getClass().getSimpleName());
		}
	}
	
	public int getSize() {
		return actions.size();
	}
	
}
