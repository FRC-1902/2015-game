package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftPCommand extends Command {

	double setpoint = 0;

    public LiftPCommand() {
        requires(Robot.lift);
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    	if(Robot.lift.topLimit.get()) Robot.lift.setRaw(0);
    	else if(Robot.lift.bottomLimit.get()) Robot.lift.home();
    		else Robot.lift.absoluteLift();
    	
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
