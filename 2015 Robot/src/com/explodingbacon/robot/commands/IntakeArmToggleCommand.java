package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmToggleCommand extends Command {

	Boolean state = null;
	
    public IntakeArmToggleCommand() {
        requires(Robot.intake);
    }
    
    public IntakeArmToggleCommand(boolean state) {
        requires(Robot.intake);
        this.state = state;
    }

    protected void initialize() {
    	if (state == null) {
    		Robot.intake.arms.set(!Robot.intake.arms.get());
    	} else {
    		Robot.intake.arms.set(state);
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
