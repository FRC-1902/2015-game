package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeToggleCommand extends Command {
	
	public Boolean state = null;
	
    public IntakeToggleCommand() {
        requires(Robot.intake);
    }
    
    public IntakeToggleCommand(boolean state) {
        requires(Robot.intake);
        this.state = state;
    }

    protected void initialize() {
    	Robot.intake.setMotors(state == null ? !Robot.intake.motorStatus : state);
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
