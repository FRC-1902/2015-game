package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmsControlCommand extends Command {

	double deadZone = 0.2;
	
    public IntakeArmsControlCommand() {
        requires(Robot.intakeArms);
    }

    protected void initialize() {
    }

    protected void execute() {
    	/*
    	if (OI.xbox.getLeftTrigger() > deadZone) {
    		Robot.intakeArms.setArm(Arm.LEFT, State.CLOSED);
    	} else {
    		Robot.intakeArms.setArm(Arm.LEFT, State.OPEN);
    	}
    	
    	if (OI.xbox.getRightTrigger() > deadZone) {
    		Robot.intakeArms.setArm(Arm.RIGHT, State.CLOSED);
    	} else {
    		Robot.intakeArms.setArm(Arm.RIGHT, State.OPEN);
    	}  	
    	*/
    	double joyX = OI.xbox.getX() / 2; 
    	if (Math.abs(joyX) < 0.1) {
    		joyX = 0;
    	}
    	
    	double joyX2 = OI.xbox.getX2() / 2; 
    	if (Math.abs(joyX2) < 0.1) {
    		joyX2 = 0;
    	}
   
    	Robot.intakeArms.leftArm.set(joyX);
    	Robot.intakeArms.rightArm.set(joyX2);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
