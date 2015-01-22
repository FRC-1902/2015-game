package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
	
	//public Talon left1 = new Talon(RobotMap.leftDriveTalon1);
	public Talon left1 = new Talon(0);
	//public Talon left2 = new Talon(RobotMap.leftDriveTalon2);
	public Talon right1 = new Talon(1);
	//public Talon right1 = new Talon(RobotMap.rightDriveTalon1);
	//public Talon right2 = new Talon(RobotMap.rightDriveTalon2);
	public Encoder leftEncoder = new Encoder(42, 42); //These are fake values
	public Encoder rightEncoder = new Encoder(99, 99); //These are fake values
	public boolean arcadeDrive = true;
	
	//Tank drive. Uses left and right inputs ranging from -1 to 1 to move the robot.
	public void tankDrive(double left, double right) {
		this.left1.set(left);
		//this.left2.set(left);
		this.right1.set(right);
		//this.right2.set(right);
		//Robot.autonomous.add(new String[]{"drive", leftEncoder.getDistance() + "", rightEncoder.getDistance()  + ""});
		if (Robot.autonomous.recording) {
			//leftEncoder.reset();
			//rightEncoder.reset();
		}
	}
	
	//Arcade drive. Uses a single joystick to move the robot.
	public void arcadeDrive(Joystick joy) {
		tankDrive(joy.getY() + joy.getX(), joy.getY() - joy.getX());
	}
	
	//Encoder drive. Uses left and right encoder distances and drives the robot until it has traveled those distances.
	public void encoderDrive(double left, double right) {
		double leftDistance = leftEncoder.getDistance();
		double rightDistance = rightEncoder.getDistance();
		leftEncoder.reset();
		rightEncoder.reset();
		boolean driving = true;
		while (driving && Robot.self.isAutonomous() && Robot.self.isEnabled()) {
			double leftSpeed = 0.5; //These variables are the left and right speeds that are adjusted
			double rightSpeed = 0.5;
			
			boolean leftPositive = leftDistance >= 0 ? true : false;
			boolean rightPositive = rightDistance >= 0 ? true : false;
			
			boolean movedLeft = true;			
			boolean movedRight = true;
			
			double moveRight = 0;
			double moveLeft = 0;
			
			boolean correctedLeft = false;
			boolean correctedRight = false;
			
			if (leftDistance > leftEncoder.getDistance()) {
				if (!leftPositive) {
					leftSpeed -= leftSpeed > 0.1 ? 0.1 : 0;
					correctedLeft = true;
				}
				moveLeft = leftSpeed;				
			} else if (leftDistance < leftEncoder.getDistance()){
				if (leftPositive) {
					leftSpeed -= leftSpeed > 0.1 ? 0.1 : 0;
					correctedLeft = true;
				}
				moveLeft = -leftSpeed;
			} else {;
				movedLeft = false;
			}
						
			if (rightDistance > rightEncoder.getDistance()) {
				if (!rightPositive) {
					rightSpeed -= rightSpeed > 0.1 ? 0.1 : 0;
					correctedRight = true;
				}
				moveRight = rightSpeed;
			} else if (rightDistance < rightEncoder.getDistance()){
				if (rightPositive) {
					rightSpeed -= rightSpeed > 0.1 ? 0.1 : 0;
					correctedRight = true;
				}
				moveRight = -rightSpeed;
			} else {
				movedRight = false;
			}
			
			if (correctedLeft || !movedRight) {
				right1.set(0);
				//right2.set(0);
			} else if (movedRight) {
				right1.set(moveRight);
				//right2.set(moveRight);
			}
			
			if (correctedRight || !movedLeft) {
				left1.set(0);
				//left1.set(0);
			} else if (movedLeft) {
				left1.set(moveLeft);
				//left1.set(moveLeft);
			}
			
			if (!movedRight && !movedLeft) {
				driving = false;
			}
		}
	}
	
	public void encoderP(double left, double right) //ermahgerd dominic code
	{
		leftEncoder.reset();
		rightEncoder.reset();
		
		double rightError, leftError;
		boolean exit = false;
		
		while(Robot.self.isAutonomous() && Robot.self.isEnabled() && !exit)
		{
			rightError = right - rightEncoder.getDistance();
			leftError = left - leftEncoder.getDistance();
			
			rightError /= 10;
			leftError /= 10;
			
			rightError = Math.abs(rightError) > 0.1 ? rightError * 0.5 : 0;
			leftError = Math.abs(leftError) > 0.1 ? rightError * 0.5 : 0;
			
			right1.set(rightError);
			left1.set(leftError);
			
			if(rightError == 0 && leftError == 0) exit = true;
		}
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveCommand());
	}
}