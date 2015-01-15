package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.OI;
import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {

    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
    	if (RobotMap.arcadeDrive) {
    		Robot.drive.arcadeDrive(OI.left);
    	} else {
    		Robot.drive.tankDrive(OI.left.getY(), OI.right.getY());
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
