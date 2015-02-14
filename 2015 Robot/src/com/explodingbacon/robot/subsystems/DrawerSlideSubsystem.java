package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Lights;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Timer;
import com.explodingbacon.robot.commands.DrawerSlideCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DrawerSlideSubsystem extends Subsystem {

	public CANTalon drawerSlide = new CANTalon(RobotMap.drawerSlidesTalonSRX);
	public boolean seizure = false;
	public Timer timer;
	
	public void setDrawerSlides(double d) {
		drawerSlide.set(d);
		if (d > 0) {
			if (!seizure && Robot.ds.isEnabled()) {
				seizure = true;
				Lights.seizure(Lights.BRAKES);
				Lights.seizure(Lights.ELEVATOR);
				Lights.seizure(Lights.ARC);
				Lights.seizure(Lights.TOTE_CHUTE);
			}
		} else if (seizure) {
			seizure = false;
			Robot.self.teleopLights();
		}
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DrawerSlideCommand());
    }
}

