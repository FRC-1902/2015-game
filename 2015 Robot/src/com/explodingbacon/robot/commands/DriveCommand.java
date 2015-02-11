package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {

    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
    	if (Robot.drive.arcadeDrive) {
    		Robot.drive.arcadeDrive(OI.left);
    	} else {
    		//Robot.drive.tankDrive(OI.left.getY(), OI.manipulator.getY());
    	}    	
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
