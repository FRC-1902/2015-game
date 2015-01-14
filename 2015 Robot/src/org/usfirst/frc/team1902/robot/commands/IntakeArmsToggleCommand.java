package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmsToggleCommand extends Command {
	
    public IntakeArmsToggleCommand() {
        requires(Robot.intake);
    }
    
    protected void initialize() {
    	Robot.intake.arms.set(!Robot.intake.arms.get());
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
