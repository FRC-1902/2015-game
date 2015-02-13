package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RecordAutonomousToggleCommand extends Command {
	
	Boolean state = null;
	
	public RecordAutonomousToggleCommand(boolean state) {
    	requires(Robot.autonomous);
    	this.state = state;
    }
	
    public RecordAutonomousToggleCommand() {
    	requires(Robot.autonomous);
    }   

    protected void initialize() {
    	if (state == null) {
    		if (Robot.autonomous.recording) {
    			//Robot.autonomous.disable();
    		} else {
    			//Robot.autonomous.enable();
    		}
    	} else {
    		//if (Robot.autonomous.recording) Robot.autonomous.disable();
    		//if (state) Robot.autonomous.enable();
    	}
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
