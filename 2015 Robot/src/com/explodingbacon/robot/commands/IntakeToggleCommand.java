package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.State;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeToggleCommand extends Command {
	
	Boolean state = null;
	double sign = 1;
	
    public IntakeToggleCommand() {
        requires(Robot.intake);
    }
    
    public IntakeToggleCommand(boolean state) {
        requires(Robot.intake);
        this.state = state;
    }
    
    public IntakeToggleCommand(State dir) {
        requires(Robot.intake);
        this.sign = dir == State.FORWARDS ? 1 : -1;
    }
    
    public IntakeToggleCommand(boolean state, State dir) {
        requires(Robot.intake);
        this.state = state;
        this.sign = dir == State.FORWARDS ? 1 : -1;
    }

    protected void initialize() {
    	if (state == null) {
    		if (Robot.intake.motorSpeed > 0 && sign == -1) {
    			Robot.intake.setMotors(-1);
    		} else {
    			Robot.intake.setMotors(Math.abs(Robot.intake.motorSpeed) > 0 ? 0 : 1 * sign);
    		}
    	} else {
    		Robot.intake.setMotors(state ? 1 * sign : 0);
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
