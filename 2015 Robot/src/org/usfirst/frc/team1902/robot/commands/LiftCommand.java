package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftCommand extends Command {

	public int motorValue;
	
    public LiftCommand(int motorValue) {
        requires(Robot.lift);
        this.motorValue = motorValue;
    }
    protected void initialize() {
    	Robot.lift.lift(motorValue);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
