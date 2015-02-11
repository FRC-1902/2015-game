package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BinGrabberToggleCommand extends Command {

	public Boolean state = null;
	
    public BinGrabberToggleCommand() {
    	requires(Robot.binGrabber);
    }
    
    public BinGrabberToggleCommand(boolean state) {
    	requires(Robot.binGrabber);
    	this.state = state;
    }

    protected void initialize() {
    	Robot.binGrabber.setGrabber(state == null ? !Robot.binGrabber.canGrabber.get() : state);
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
