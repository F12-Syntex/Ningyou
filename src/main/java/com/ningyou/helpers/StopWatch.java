package com.ningyou.helpers;

public class StopWatch {

	public StopWatch() {
		this.start();
	}
	
	private long start;

	public void start() {
		this.start = System.currentTimeMillis();
	}
	public long stamp() {
		long time = System.currentTimeMillis() - start;
		return time;
	}
	
	public int getCurrentTimeInSeconds() {
		int time = (int)( System.currentTimeMillis() - start ) / 1000;
		return time;
	}
	
	public void reset() {
		this.start();
	}
	
	/**
	 * 
	 * @param i
	 * @returns the amount of time the task ran in seconds
	 */
	public static StopWatch runTask(Runnable i) {
		
		StopWatch watch = new StopWatch();
		
		watch.start();
		i.run();
		
		return watch;
	}

}
