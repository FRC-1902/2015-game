package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftPistonCommand extends Command {

    protected void initialize() {
    	Robot.lift.setPiston(true);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return !Robot.oi.liftPiston.get();
    }

    protected void end() {
    	Robot.lift.setPiston(false);
    }

    protected void interrupted() {
    	Robot.lift.setPiston(false);
    }
}
