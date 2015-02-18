package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Lights.Strip;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.commands.DrawerSlideCommand;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DrawerSlideSubsystem extends Subsystem {

	public CANTalon drawerSlide = new CANTalon(RobotMap.drawerSlidesTalonSRX);
	public boolean seizure = false;
	
	public void setDrawerSlides(double d) {
		if (Math.abs(d) > 0.1) {
			drawerSlide.set(d);
		} else {
			drawerSlide.set(0);
		}
		if (d > 0) {
			if (!seizure && Robot.ds.isEnabled()) {
				seizure = true;
				Strip.BACK.seizure();
				Strip.ELEVATOR.seizure();
				Strip.ARC.seizure();
				Strip.TOTE_CHUTE.seizure();
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

