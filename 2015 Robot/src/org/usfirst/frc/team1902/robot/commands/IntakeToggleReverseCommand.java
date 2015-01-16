package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeToggleReverseCommand extends Command {

	boolean reversed;
	
    public IntakeToggleReverseCommand(boolean reversed) {
        requires(Robot.intake);
        this.reversed = reversed;
    }

    protected void initialize() {
    	Robot.intake.setReversed(reversed);
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
