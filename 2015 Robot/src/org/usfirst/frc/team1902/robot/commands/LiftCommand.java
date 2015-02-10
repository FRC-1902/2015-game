package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LiftCommand extends Command {

	public double motorValue;
	
    public LiftCommand(double motorValue) {
        requires(Robot.lift);
        this.motorValue = motorValue;
    }
    protected void initialize() {
    	Robot.lift.setRaw(motorValue);
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
