package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RecordAutonomousToggleCommand extends Command {

	public Boolean state = null;
	
    public RecordAutonomousToggleCommand() {
    	requires(Robot.recordedAutonomous);
    }
    
    public RecordAutonomousToggleCommand(boolean state) {
    	requires(Robot.recordedAutonomous);
    	this.state = state;
    }

    protected void initialize() {    	
    	if (state == true) {
    		Robot.recordedAutonomous.enable();
    	} else {
    		Robot.recordedAutonomous.disable();
    	}
    	Robot.recordAutonomous = state == null ? !Robot.recordAutonomous : state;
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
