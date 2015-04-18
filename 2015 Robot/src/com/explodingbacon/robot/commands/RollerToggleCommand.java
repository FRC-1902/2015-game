package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;

public class RollerToggleCommand extends Command {

	Boolean status = null;
	
	public RollerToggleCommand() {
        requires(Robot.intake);
    }
	
    public RollerToggleCommand(boolean status) {
        requires(Robot.intake);
        this.status = status;
    }

    protected void initialize() {
    	if (status == null) { 
    		Robot.intake.setRoller(Robot.intake.roller.get() == Value.kOn ? false : true);
    	} else {
    		Robot.intake.setRoller(status);
    	}
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
