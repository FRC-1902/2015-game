package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PushToteCommand extends Command {

    public PushToteCommand() {
        requires(Robot.lift);
    }

    protected void initialize() {
    	Robot.lift.pushTote();
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
