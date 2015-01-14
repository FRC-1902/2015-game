package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeToggleCommand extends Command {
	
    public IntakeToggleCommand() {
        requires(Robot.intake);
    }

    protected void initialize() {
    	Robot.intake.setMotors(!Robot.intake.motorStatus);
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
