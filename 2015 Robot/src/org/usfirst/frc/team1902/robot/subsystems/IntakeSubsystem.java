package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
    public Talon left;
    public Talon right;
    public Solenoid arms;
    public boolean motorStatus = false;
    public boolean reverse = false;

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
    		if (reverse) {
    			left.set(-1);
    			right.set(-1);
    		} else {
    			left.set(1);
        		right.set(1);
    		}
    	}
    	motorStatus = status;
    	Robot.addToAuto("intakeMotor|" + status + "]");
    }
    
    public void setArms(boolean status) {
    	arms.set(status);
    	Robot.addToAuto("intakeArms|" + status + "]");
    }
    
    public void setReversed(boolean status) {
    	reverse = status;
    	Robot.addToAuto("reverseMotor|" + status + "]");
    }
    
    public void initDefaultCommand() {
    }
}

