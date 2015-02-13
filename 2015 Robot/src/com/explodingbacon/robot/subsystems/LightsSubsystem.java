package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LightsSubsystem extends Subsystem {
    
    private SerialPort rioDuino = new SerialPort(9600, SerialPort.Port.kMXP);
    
    public boolean send(Char[] array) {
    	String data = "";
    	for (Char c : array) {
    		data = data + c.data;
    	}
    	return sendRaw(data);
    }
    
    public boolean sendRaw(String data) {
    	if(rioDuino.writeString(data) == data.length()) return true;
    	else return false;
    }
    
    public enum Char {
    	TEST("t"),
    	BRAKE("?"),
    	EVEVATOR("?"),
    	ARC("?"),
    	
    	WHITE("?"),
    	BLACK("?"),
    	ORANGE("?"),
    	GREEN("?");
    	
    	String data;
    	
    	Char(String s) {
    		this.data = s;
    	}
    }
    

    public void initDefaultCommand() {   	
    }
}

