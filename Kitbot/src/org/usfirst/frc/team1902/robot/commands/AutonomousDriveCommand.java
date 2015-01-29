package org.usfirst.frc.team1902.robot.commands;

import org.usfirst.frc.team1902.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
public class AutonomousDriveCommand extends Command {
	
	public double left;
	public double right;
	public double time;
	
	public AutonomousDriveCommand(double left, double right, double time) {
		requires(Robot.drive);
		this.left = left;
		this.right = right;   
		this.time = time;
	}
	
	@Override
	protected void initialize() {
		Robot.drive.tankDrive(left, right);
		Timer.delay(time);
	}

	@Override
	public void execute() {
	}

	@Override
	protected boolean isFinished() {
		Robot.drive.tankDrive(0, 0);
		return true;
	}

	@Override
	protected void end() {		
	}

	@Override
	protected void interrupted() {		
	}
}
