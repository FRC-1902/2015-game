package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmsToggleCommand extends Command {
	
	public Boolean state = null;
	
    public IntakeArmsToggleCommand() {
        requires(Robot.intake);
    }
    
    public IntakeArmsToggleCommand(boolean state) {
        requires(Robot.intake);
        this.state = state;
    }
    
    protected void initialize() {
    	Robot.intake.setArms(state == null ? !Robot.intake.arms.get() : state);
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
