package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;

public class RollerToggleCommand extends Command {

	Boolean state;
	
	public RollerToggleCommand() {
        requires(Robot.intake);
    }
	
    public RollerToggleCommand(boolean state) {
        requires(Robot.intake);
        this.state = state;
    }

    protected void initialize() {
    	if (state == null) {  		
    		Robot.intake.setRoller(Robot.intake.roller.get() == Value.kForward ? false : true);
    	} else {
    		Robot.intake.setRoller(state);
    	}
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
