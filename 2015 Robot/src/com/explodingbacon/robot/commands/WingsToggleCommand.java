package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class WingsToggleCommand extends Command {

	public Boolean state = null;
	
    public WingsToggleCommand() {
    	requires(Robot.wings);
    }
    
    public WingsToggleCommand(boolean state) {
    	requires(Robot.wings);
    	this.state = state;
    }

    protected void initialize() {
    	Robot.wings.wings.set(state == null ? !Robot.wings.wings.get() : state);
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
