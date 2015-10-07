package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.ControlType;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {
	
    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {}

    protected void execute() {
    	if (Robot.controlType == ControlType.NORMAL) {
    		if (Robot.arcadeDrive) {
    			Robot.drive.arcadeDrive(OI.left);
    		} else {
    			Robot.drive.tankDrive(OI.left.getY(), OI.right.getY());
    		}
    	} else {
    		if (Math.abs(OI.xbox.getX2()) >= 0.1 || Math.abs(OI.xbox.getY2()) >= 0.1) { //Deadzone, since sometimes wheels like to move by themselves
    			Robot.drive.arcadeDrive(OI.xbox.getX2(), OI.xbox.getY2());
    		} else {
    			Robot.drive.arcadeDrive(0, 0);
    		}
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
