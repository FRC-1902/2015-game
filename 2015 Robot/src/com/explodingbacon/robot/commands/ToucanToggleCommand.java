package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToucanToggleCommand extends Command {

    public ToucanToggleCommand() {
        requires(Robot.toucans);
    }

    protected void initialize() {
    	Robot.toucans.set(!Robot.toucans.get());
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
