package com.explodingbacon.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LightsSubsystem extends Subsystem {
    
    private SerialPort rioDuino = new SerialPort(9600, SerialPort.Port.kMXP);
    
    public boolean send(String data) {
    	if(rioDuino.writeString(data) == data.length()) return true;
    	else return false;
    }

    public void initDefaultCommand() {   	
    }
    
    //colors: orange green white black
    //
}

