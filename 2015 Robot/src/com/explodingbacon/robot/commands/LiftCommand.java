package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LiftCommand extends Command {

	public double value = 0;
	
    public LiftCommand(double value) {
        requires(Robot.lift);
        this.value = value;
    }

    protected void initialize() {
    	Robot.lift.setRaw(value);
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
