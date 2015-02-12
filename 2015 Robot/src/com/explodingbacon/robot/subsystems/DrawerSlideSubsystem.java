package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.commands.DrawerSlideCommand;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DrawerSlideSubsystem extends Subsystem {

	public Talon drawerSlide = new Talon(RobotMap.drawerSlidesTalon);
	
	public void setDrawerSlides(double d) {
		drawerSlide.set(d);
		Robot.autonomous.add(new String[]{"drawerSlides", d + ""});
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DrawerSlideCommand());
    }
}

