package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class TuskCommand extends Command {

    public TuskCommand() {
        requires(Robot.tusks);
    }

    protected void initialize() {}

    protected void execute() {
    	double speed = OI.xbox.getLeftTrigger() - OI.xbox.getRightTrigger();
    	if (Math.abs(speed) > 0.1) {
    		Robot.tusks.set(speed * 0.5);
    	} else {
    		Robot.tusks.set(0);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}