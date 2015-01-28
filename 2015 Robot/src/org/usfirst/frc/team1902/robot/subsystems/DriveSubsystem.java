package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

	//public Talon left1 = new Talon(RobotMap.leftDriveTalon1);
	public Talon left1 = new Talon(1);
	//public Talon left2 = new Talon(RobotMap.leftDriveTalon2);
	public Talon right1 = new Talon(0);
	//public Talon right1 = new Talon(RobotMap.rightDriveTalon1);
	//public Talon right2 = new Talon(RobotMap.rightDriveTalon2);
	public Encoder leftEncoder = new Encoder(0, 1);
	public Encoder rightEncoder = new Encoder(2, 3, true);
	//public DigitalInput leftSensor = new DigitalInput(0);
	//public DigitalInput rightSensor = new DigitalInput(1);
	public int encoderSkip = 0;
	public boolean arcadeDrive = true;
	
	public DriveSubsystem() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	//Tank drive. Uses left and right inputs ranging from -1 to 1 to move the robot.
	public void tankDrive(double left, double right) {
		this.left1.set(left);
		//this.left2.set(left);
		this.right1.set(right);
		//this.right2.set(right);
		//System.out.println("Left encoder: " + leftEncoder.getDistance());
		//System.out.println("Right encoder: " + rightEncoder.getDistance());
		//encoderSkip++;
		//if (encoderSkip == 10) {
			Robot.autonomous.add(new String[]{"drive", leftEncoder.getDistance() + "", rightEncoder.getDistance()  + ""});
			if (Robot.autonomous.recording) {
				//leftEncoder.reset();
				//rightEncoder.reset();
			}
			encoderSkip = 0;
		//}
	}

	//Arcade drive. Uses a single joystick to move the robot.
	public void arcadeDrive(Joystick joy) {
		tankDrive(joy.getY() - joy.getX(), joy.getY() + joy.getX());
	}

	//Encoder drive. Takes a left and right encoder value and plugs them into a P loop.
	public void encoderDrive(double left, double right) {
		double rightError, leftError;
		boolean exit = false;
		
		//right = Math.random() * 10 + 1;
		//left = Math.random() * 10 + 1;
		//if(left/abs(left) != right/abs(right)
		//right = 100;
		//left = 100;

		double kP = 75;
		double min = 0.3;
		double max = 0.75;
		boolean turning = false;
		
		rightError = right - rightEncoder.getDistance();
		leftError  = left - leftEncoder.getDistance();
		
		if (leftError != 0 && rightError != 0) {
			if(leftError/Math.abs(leftError) != rightError/Math.abs(rightError)) turning = true;
		}
		
		if(Math.abs(leftError-rightError) > 90) turning = true;
		
		if(Math.max(leftError, rightError) / Math.min(leftError, rightError) > 1.33) turning = true;

		if (turning) {
			kP = 40;
			min = 0.4;
			max = 0.85;
		}
		
		System.out.println("Got a command to drive to '" + left + "' and '" + right + "'.");
		while(!exit && Robot.self.isAutonomous() && Robot.self.isEnabled()) {
			rightError = right - rightEncoder.getDistance();
			leftError  = left - leftEncoder.getDistance();

			rightError /= kP;
			leftError /= kP;
			
			//rightError = rightError < 0 ? -Math.sqrt(-rightError) : Math.sqrt(rightError);
			//leftError = leftError < 0 ? -Math.sqrt(-leftError) : Math.sqrt(leftError);
			
			if (Math.abs(rightError) < min) {
				/*
				if (Math.abs(rightError) >= 0.15) {
					rightError = rightError < 0 ? (0.2 / Math.abs(rightError)) * rightError : rightError;
				} else {
					rightError = 0;
				}
				*/
				rightError = 0;
			}
			
			if (Math.abs(leftError) < min) {
				/*
				if (Math.abs(leftError) >= 0.15) {
					leftError = leftError < 0 ? (0.2 / Math.abs(leftError)) * leftError : leftError;
				} else {
					leftError = 0;
				}
				*/
				leftError = 0;
			}
			
			rightError = Math.abs(rightError) > max ? Math.abs(max/rightError)*rightError: rightError;
			leftError = Math.abs(leftError) > max ? Math.abs(max/leftError)*leftError : leftError;                                                                                                                                                                                                                                                                                                     

			System.out.println("Right is driving at " + rightError + " to " + right);
			System.out.println("Left is driving at " + leftError + " to " + left);
			
			right1.set(rightError);
			left1.set(leftError);

			if(rightError == 0 && leftError == 0) exit = true;
		}
		System.out.println("Encoder drive finished!");	
		//rightEncoder.reset();
		//leftEncoder.reset();
		//Timer.delay(1);
	}

	public boolean adjustToTote() {
		/*
		double adjustSpeed = 0.5;
		Robot.autonomous.add(new String[]{"adjustToTote"});		
		if (leftSensor.get() && !rightSensor.get()) {
			tankDrive(-adjustSpeed, adjustSpeed);
		} else if (rightSensor.get() && !leftSensor.get()) {
			tankDrive(adjustSpeed, -adjustSpeed);
		} else {
			return true;
		}
		*/
		return false;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveCommand());
	}
}