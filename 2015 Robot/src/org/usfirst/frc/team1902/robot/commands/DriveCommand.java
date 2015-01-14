package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.OI;
import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveCommand extends Command {

    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    //Just change which line is commented out to change whether we use Arcade Drive or Tank Drive
    protected void execute() {
    	Robot.drive.arcadeDrive(OI.left);
    	//Robot.drive.tankDrive(OI.left.getY(), OI.right.getY());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
