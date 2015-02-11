package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDriveCommand extends Command {
	
	public TankDriveCommand() {
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {	
	}

	//Switch which line is commented out to switch between Tank Drive and Arcade Drive.
	//The top line is Tank Drive, the bottom line is Arcade Drive.
	@Override
	public void execute() {
		//Robot.drive.tankDrive(Robot.oi.left.getX(), Robot.oi.right.getX());
		Robot.drive.arcadeDrive(Robot.oi.left);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {		
	}

	@Override
	protected void interrupted() {		
	}
}
