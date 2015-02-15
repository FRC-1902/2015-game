package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.XboxController.Direction;
import edu.wpi.first.wpilibj.command.Command;

public class LiftControlCommand extends Command {

    public LiftControlCommand() {
        requires(Robot.lift);
    }

    protected void initialize() {
    }

	protected void execute() {
		Direction d = OI.xbox.getDPad();
		if (d.isNorth()) {
			Robot.lift.setRaw(-0.5);
		} else if (d.isSouth()) {
			Robot.lift.setRaw(0.5);
		} else {
			Robot.lift.setRaw(0);
		}
		System.out.println("Lift encoder: " + Robot.lift.liftEncoder.getRaw());
	}

	protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
