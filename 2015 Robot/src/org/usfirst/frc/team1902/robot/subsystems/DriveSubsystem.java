package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
	
	public Talon left = new Talon(RobotMap.leftDriveTalon);
	public Talon right = new Talon(RobotMap.rightDriveTalon);
	
	//Tank drive. Uses a left and right value ranging from -1 to 1 to move the robot.
	public void tankDrive(double left, double right) {
		this.left.set(left);
		this.right.set(right);
	}
	
	//Arcade drive. Uses a single joystick to move the robot.
	public void arcadeDrive(Joystick joy) {
		this.left.set(joy.getX() + joy.getY());
		this.right.set(joy.getX() - joy.getY());
	}
	
	public void initDefaultCommand() {
		
	}
}
