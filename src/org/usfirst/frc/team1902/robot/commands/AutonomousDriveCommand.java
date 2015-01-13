package org.usfirst.frc.team1902.robot.commands;

public class AutonomousDriveCommand extends DriveCommand {
	
	public double left;
	public double right;
	public double time;
	
	public AutonomousDriveCommand(double left, double right, double time) {
		this.left = left;
		this.right = right;   
		this.time = time;
	}
	
	@Override
	public void execute() {
		drive(left, right, time);
	}
}
