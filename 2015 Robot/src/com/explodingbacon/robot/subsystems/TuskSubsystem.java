package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.commands.TuskCommand;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TuskSubsystem extends Subsystem {

	public CANTalon tusks = new CANTalon(RobotMap.tusksTalonSRX);
	
	public double get() {
		return tusks.get();
	}
	
	public void set(double d) {
		tusks.set(d);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new TuskCommand());
    }
}

