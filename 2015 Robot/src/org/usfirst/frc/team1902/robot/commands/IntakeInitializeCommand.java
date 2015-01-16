package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeInitializeCommand extends Command {

    public IntakeInitializeCommand() {
        requires(Robot.intake);
    }

    protected void initialize() {
    	Robot.intake.roller.set(1);
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
