package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ToteStopSubsystem extends Subsystem {
    
	public Solenoid toteStop = new Solenoid(RobotMap.toteStopSolenoid);
	
	public void set(boolean state) {
		toteStop.set(state);
	}

    public void initDefaultCommand() {}
}

