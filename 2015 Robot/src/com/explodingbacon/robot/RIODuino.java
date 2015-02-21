package com.explodingbacon.robot;

import java.util.ArrayList;
import java.util.List;

public class RIODuino extends CodeThread {
	
	private List<byte[]> bytesToAdd = new ArrayList<>();
	private List<byte[]> bytesToWrite = new ArrayList<>();	
	//private I2C i2c = new I2C(Port.kMXP, 0xA0);
		
	public RIODuino() {
		//start();
	}
	
	public void send(byte[] bytes) {
		bytesToAdd.add(bytes);
	}		
	
	@Override
	public void code() {
		/*
		for (int i=0; i < bytesToAdd.size(); i++) {
			bytesToWrite.add(bytesToAdd.get(i));
		}
		for (int i=0; i < bytesToWrite.size(); i++) {
			byte[] current = bytesToWrite.get(i);
			if (bytesToAdd.contains(current)) {
				bytesToAdd.remove(current);
			}
		}
		for (byte[] bytes : bytesToWrite) {
			for (byte b : bytes) {
				i2c.write(0x00, b);
			}
		}
		bytesToWrite.clear();
		*/
	}	
} 
