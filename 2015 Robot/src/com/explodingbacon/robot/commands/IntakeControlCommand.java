package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeControlCommand extends Command {

	public IntakeControlCommand() {
		requires(Robot.intake);
	}
	
	@Override
	protected void initialize() {}

	@Override
	protected void execute() {
		if (Robot.intake.control) {
			/*
			double speed = 0;
			boolean closed;
			if (Robot.oi.intake.get()) {
				speed = 1;
			} else if (Robot.oi.reversedIntake.get()) {
				speed = -1;
			}
			if (Robot.oi.intakeArms.get()) {
				if (speed != 0) {
					closed = false;
				} else {
					closed = true;
				}
			} else {
				if (speed != 0) {
					closed = true;
				} else {
					closed = false;
				}
			}
			
			Robot.intake.setMotors(speed);
			Robot.intake.arms.set(closed);
			*/
			
			if (Robot.oi.roller.get()) {
				Robot.intake.tempRoller.set(0);
			} else {
				Robot.intake.tempRoller.set(1);
			}
			
			Robot.toteStop.set(!Robot.oi.toteStop.get());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
