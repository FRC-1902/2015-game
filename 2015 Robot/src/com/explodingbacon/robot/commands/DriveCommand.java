package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.ControlType;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {
	
	int scalar = 10;
	
    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {}

    boolean lastHeld = false;

    protected void execute() {
    	if (Robot.controlType != ControlType.SINGLEPLAYER) {
    		if (Robot.arcadeDrive) {
    			Robot.drive.arcadeDrive(OI.left);
    		} else {
    			Robot.drive.tankDrive(OI.left.getY(), OI.right.getY());
    		}
    	} else {
    		if (Math.abs(OI.xbox.getX2()) > 0.1 || Math.abs(OI.xbox.getY2()) > 0.1) {
    		Robot.drive.arcadeDrive(OI.xbox.getX2(), OI.xbox.getY2());
    		} else {
    			Robot.drive.arcadeDrive(0, 0);
    		}
    	}
    	if (Robot.oi.liftWithDrive.get()) {
			if (lastHeld) {
				int driveChangeClicks = ((Robot.drive.leftEncoder.getRaw() + Robot.drive.rightEncoder.getRaw()) / 2);
				Robot.lift.target += (driveChangeClicks / scalar);
				Robot.drive.leftEncoder.reset();
				Robot.drive.rightEncoder.reset();
			} else {
				lastHeld = true;
				Robot.drive.leftEncoder.reset();
				Robot.drive.rightEncoder.reset();
			}
		} else {
			lastHeld = false;
		}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
