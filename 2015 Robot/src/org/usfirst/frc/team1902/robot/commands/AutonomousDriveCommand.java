package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousDriveCommand extends Command {

	public double left;
	public double right;
	public double time;
	
    public AutonomousDriveCommand(double left, double right, double time) {
        requires(Robot.drive);
        this.left = left;
        this.right = right;
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.tankDrive(left, right);
    	Timer.delay(time);
    	Robot.drive.tankDrive(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
