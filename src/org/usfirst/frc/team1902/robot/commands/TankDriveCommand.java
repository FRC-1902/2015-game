package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

public class TankDriveCommand extends DriveCommand {
	
	@Override
	public void execute() {
		drive(Robot.oi.left.getX(), Robot.oi.right.getX());
	}
}
