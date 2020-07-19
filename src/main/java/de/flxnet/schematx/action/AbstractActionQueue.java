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
public class AbstractActionQueue implements Runnable {

	@Getter
	private String name;
	
	@Getter @Setter
	private boolean debug;
	
	@Getter
	private Queue<AbstractAction> actions;
	
	@Getter
	private long lastMillis = 0;
	
	@Getter @Setter
	private int interval = 10;
	
	public AbstractActionQueue(String name, boolean debug) {
		this.name = name;
		this.debug = debug;
		this.actions = Queues.newConcurrentLinkedQueue();
	}
	
	public void process(AbstractAction action) {
		this.actions.add(action);
	}

	@Override
	public void run() {
		long currentMillis = System.currentTimeMillis();
		
		if(actions.size() == 0) return;
		
		if((currentMillis - lastMillis) >= interval) {
			lastMillis = currentMillis;
			
			IAction action = actions.poll();
			if(action == null) return;
			
			if(debug) System.out.println("[" + name + "] Running " + action.getClass().getSimpleName());
			action.run();
		}
	}
	
	public int getSize() {
		return actions.size();
	}
	
}
