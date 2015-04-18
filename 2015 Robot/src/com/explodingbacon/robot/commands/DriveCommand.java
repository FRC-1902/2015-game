package com.explodingbacon.robot.commands;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {

    public DriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {}

    boolean lastHeld = false;
    
    protected void execute() {
    	Robot.drive.arcadeDrive(OI.left);
    	if (Robot.oi.liftWithDrive.get()) {
			if (lastHeld) {
				Robot.lift.target += (Robot.drive.leftEncoder.getRaw() + Robot.drive.rightEncoder.getRaw()) / 2;
				Robot.drive.leftEncoder.reset();
				Robot.drive.rightEncoder.reset();
			} else {
				lastHeld = true;
				Robot.drive.leftEncoder.reset();
				Robot.drive.rightEncoder.reset();
			}
		} else {
			lastHeld = false;
		}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
