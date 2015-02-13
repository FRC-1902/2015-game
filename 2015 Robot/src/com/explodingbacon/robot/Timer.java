package com.explodingbacon.robot;

public class Timer extends Thread {

	double seconds;
	TimerUser user;
	boolean loop = true;
	boolean stop = false;
	
	public Timer(double seconds, TimerUser user) {
		this.seconds = seconds;
		this.user = user;
	}
	
	public Timer(double seconds, boolean loop, TimerUser user) {
		this.seconds = seconds;
		this.loop = loop;
		this.user = user;
	}
	
	/**
	 * Starts the thread. Use this instead of start().
	 * @return This timer. To be used for method chaining.
	 */
	public Timer begin() {
		super.start();
		return this;
	}
	
	/**
	 * The loop that makes this timer work. Don't call this.
	 */
	@Override
	public void run() {
		user.timerBegin();
		while (true) {
			try {
				if (stop) break;
				Thread.sleep((long) seconds * 1000);
				user.timer();
				if (!loop) break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		user.timerHalt();
	}

	/**
	 * Stops this thread. Use this instead of stop() or any other related functions.
	 */
	public void halt() {
		stop = true;
	}	
}