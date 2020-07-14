package de.flxnet.schematx.helper;

import java.text.DecimalFormat;

/**
 * Software by FLXnet More info at FLXnet.de Copyright (c) 2015-2020 by FLXnet
 * 
 * @author Felix
 */
public class LagMeter implements Runnable {

	public static int TICK_COUNT = 0;
	public static long[] TICKS = new long[600];
	public static long LAST_TICK = 0L;

	public void run() {
		TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();
		TICK_COUNT += 1;
	}
	
	public static double getTPS() {
		return getTPS(100);
	}

	public static double getTPS(int ticks) {
		if (TICK_COUNT < ticks) {
			return 20.0D;
		}
		int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
		long elapsed = System.currentTimeMillis() - TICKS[target];

		double result = ticks / (elapsed / 1000.0D);
		return result > 20 ? 20.00 : result;
	}

	public static long getElapsed(int tickID) {
		if (TICK_COUNT - tickID >= TICKS.length) {
		}

		long time = TICKS[(tickID % TICKS.length)];
		return System.currentTimeMillis() - time;
	}
	
	public static String formatTps(double tps) {
		DecimalFormat tpsFormat = new DecimalFormat("#.##");
		String formattedTps = tpsFormat.format(tps);
		
		if(tps > 19) {
			return "§2" + formattedTps;
		}
		
		if(tps > 14) {
			return "§e" + formattedTps;
		}
		
		if(tps > 9) {
			return "§c" + formattedTps;
		}
		
		if(tps < 9) {
			return "§4" + formattedTps;
		}
		
		return "§f--.--";
	}
	
	public static String getTpsString() {
		return formatTps(getTPS(20)) + "§7, " + formatTps(getTPS(60)) + "§7, " + formatTps(getTPS(100));
	}

}
