package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class CanGrabberToggleCommand extends Command {

	public Boolean state = null;
	
    public CanGrabberToggleCommand() {
    	requires(Robot.canGrabber);
    }
    
    public CanGrabberToggleCommand(boolean state) {
    	requires(Robot.canGrabber);
    	this.state = state;
    }

    protected void initialize() {
    	Robot.canGrabber.setGrabber(state == null ? !Robot.canGrabber.canGrabber.get() : state);
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
