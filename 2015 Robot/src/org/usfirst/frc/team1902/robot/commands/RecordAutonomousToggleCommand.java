package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RecordAutonomousToggleCommand extends Command {
	
    public RecordAutonomousToggleCommand() {
    	requires(Robot.autonomous);
    }   

    protected void initialize() { 
    	if (Robot.autonomous.recording) {
    		Robot.autonomous.disable();
    	} else {
    		Robot.autonomous.enable();
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
