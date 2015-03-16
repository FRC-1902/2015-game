package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftPistonCommand extends Command {
	
    public LiftPistonCommand() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.setPiston(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.oi.liftPiston.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.setPiston(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.setPiston(false);
    }
}
