package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.commands.DrawerSlidesCommand;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DrawerSlideSubsystem extends Subsystem {

	public CANTalon drawerSlides = new CANTalon(RobotMap.drawerSlidesSRX);
	
	public double get() {
		return drawerSlides.get();
	}
	
	public void set(double d) {
		drawerSlides.set(d);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DrawerSlidesCommand());
    }
}

