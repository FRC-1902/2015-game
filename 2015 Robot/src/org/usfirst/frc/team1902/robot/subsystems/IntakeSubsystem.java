package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
    public Talon left;
    public Talon right;
    public Solenoid arms;
    public boolean motorStatus = false;

    public IntakeSubsystem() {
    	left = new Talon(RobotMap.leftIntakeTalon);
    	right = new Talon(RobotMap.rightIntakeTalon);
    	arms = new Solenoid(RobotMap.intakeArmsSolenoid);
    }
    
    public void setMotors(boolean status) {
    	if (status == false) {
    		left.set(0);
    		right.set(0);
    	} else {
    		left.set(1);
    		right.set(1);
    	}
    	motorStatus = status;
    }
    
    public void initDefaultCommand() {
    }
}

