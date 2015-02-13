package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LightsSubsystem extends Subsystem {
    
    private SerialPort rioDuino = new SerialPort(9600, SerialPort.Port.kMXP);
    
    public boolean send(byte[] data)
    {
    	if(rioDuino.write(data, 1) == 1) return true;
    	else return false;
    }

    public void initDefaultCommand() {
    	
    }
    
    public enum Strip
    {
    	BRAKE,
    	EVEVATOR,
    	ARC
    }
}

