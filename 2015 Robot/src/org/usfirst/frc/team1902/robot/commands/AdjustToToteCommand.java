package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class AdjustToToteCommand extends Command {

	boolean success = false;
	
    public AdjustToToteCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
    	success = Robot.drive.adjustToTote();
    }

    protected boolean isFinished() {
        return success;
    }

    protected void end() {
    	Robot.drive.tankDrive(0, 0);
    }

    protected void interrupted() {
    	Robot.drive.tankDrive(0, 0);
    }
}
