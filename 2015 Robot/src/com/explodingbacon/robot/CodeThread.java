package com.explodingbacon.robot;

public class CodeThread implements Runnable {

	Thread t;
	boolean stop = false;
	
	public void start() {
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		while (true) {
			if (stop) break;
			code();
		}
		t = null;		
	}
	
	/**
	 * Override this with what code you want to run looped.
	 */
	public void code() {}
	
	public void stop() {
		stop = true;
	}
	
}
