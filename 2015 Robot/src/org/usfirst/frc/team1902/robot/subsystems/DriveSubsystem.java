package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;
import org.usfirst.frc.team1902.robot.Util;
import org.usfirst.frc.team1902.robot.commands.DriveCommand;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends Subsystem {

	public VictorSP left1 = new VictorSP(RobotMap.leftDriveVictor1);
	public VictorSP left2 = new VictorSP(RobotMap.leftDriveVictor2);
	public VictorSP right1 = new VictorSP(RobotMap.rightDriveVictor1);
	public VictorSP right2 = new VictorSP(RobotMap.rightDriveVictor2);
	public Encoder leftEncoder = new Encoder(RobotMap.leftEncoderID1, RobotMap.leftEncoderID2, true);
	public Encoder rightEncoder = new Encoder(RobotMap.rightEncoderID1, RobotMap.rightEncoderID2);
	public Gyro gyro = new Gyro(RobotMap.gyro);
	public boolean arcadeDrive = true;
	
	public double encoderKP = 0.01;
	public double encoderKAngleP = 0.15;
	public double encoderMin = 0.3;
	public double encoderMax = 0.5;
	
	public double gyroKP = 0.02;
	public double gyroKI = 0.00025;
	public double gyroKI2 = 0.000025;
	public double gyroMin = 0.2;
	public double gyroMax = 0.4;
	
	public DriveSubsystem() {
		leftEncoder.setDistancePerPulse(1);
		rightEncoder.setDistancePerPulse(1);
		leftEncoder.reset();
		rightEncoder.reset();
		gyro.reset();
		if (Robot.self.isTest()) {
			SmartDashboard.putNumber("encoderKP", encoderKP);
			SmartDashboard.putNumber("encoderKAngleP", encoderKAngleP);
			SmartDashboard.putNumber("encoderMin", encoderMin);
			SmartDashboard.putNumber("encoderMax", encoderMax);
			SmartDashboard.putNumber("gyroKP", gyroKP);
			SmartDashboard.putNumber("gyroKI", gyroKI);
			SmartDashboard.putNumber("gyroKI2", gyroKI2);
			SmartDashboard.putNumber("gyroMin", gyroMin);
			SmartDashboard.putNumber("gyroMax", gyroMax);
		}
	}

	/**
	 * Tank drive. Uses left and right inputs ranging from -1 to 1 to drive the robot.
	 **/
	public void tankDrive(double left, double right) {
		this.left1.set(-left);
		//this.left2.set(left);
		this.right1.set(right);
		//this.right2.set(right);
		Robot.autonomous.add(new String[]{"drive", leftEncoder.getRaw() + "", rightEncoder.getRaw()  + "", gyro.getAngle() + ""});
		if (Robot.autonomous.recording) {
			leftEncoder.reset();
			rightEncoder.reset();
		}
	}

	/**
	 * Arcade drive. Uses a single joystick to drive the robot.
	 **/
	public void arcadeDrive(Joystick joy) {
		tankDrive(joy.getY() - joy.getX(), joy.getY() + joy.getX());
	}

	/**
	 * Encoder drive. P loop that drives to the left and right encoder values.
	 **/
	public void encoderDrive(double left, double right, double nextLeft, double nextRight) {
		gyroTurn(Robot.angle, true);
		leftEncoder.reset();
		rightEncoder.reset();
		double rightError, leftError, angleError;
		boolean exit = false;

		double kP = SmartDashboard.getNumber("encoderKP", encoderKP); //Tuning variable for the proportional term
		double kAngleP = SmartDashboard.getNumber("encoderKAngleP", encoderKAngleP); //Tuning variable for the proportional term of heading correction
		double min = SmartDashboard.getNumber("encoderMin", encoderMin); //The minimum speed we want to move before saying we're arrived.
		double max = SmartDashboard.getNumber("encoderMax", encoderMax); //The maximum speed we want to move before capping the speed.		
		double lastCorrectLeft = 0;
		double lastCorrectRight = 0;
		
		rightError = right - rightEncoder.getRaw();
		leftError  = left - leftEncoder.getRaw();		

		double rightSign = Util.sign(rightError);
		double leftSign = Util.sign(leftError);
		
		System.out.println("Got a command to drive to '" + left + "' and '" + right + "'.");
		while(!exit && Robot.self.isAutonomous() && Robot.self.isEnabled()) {
			rightError = right - rightEncoder.getRaw();
			leftError  = left - leftEncoder.getRaw();
			angleError = Robot.angle - gyro.getAngle();

			rightError *= kP;
			leftError *= kP;
			angleError *=  kAngleP;
			
			rightError = Util.minMax(rightError, min, max);
			leftError = Util.minMax(leftError, min, max);
			System.out.println("Gyro: " + gyro.getAngle() + ", AngleError: " + angleError);
			
			if (nextLeft != 0 && nextRight != 0) {
				if (Util.sign(rightError) != rightSign && Util.sign(leftError) != leftSign) {
					if (nextRight - Robot.drive.rightEncoder.getRaw() < nextRight - lastCorrectRight && nextLeft - Robot.drive.leftEncoder.getRaw() > nextLeft - lastCorrectLeft) {
						rightError = 0;
						leftError = 0;
					}
				} else if (Util.sign(rightError) != rightSign) {
					lastCorrectRight = Robot.drive.rightEncoder.getRaw();
				} else {
					lastCorrectLeft = Robot.drive.leftEncoder.getRaw();
				}
			}
			
			if (Math.abs(angleError) > 1) { //angleError is in degrees right now
				angleError = Util.minMax(angleError * kAngleP, 0, max);
				leftError -= angleError;
				rightError += angleError;
				//System.out.println("Adjusted driving by " + angleError + " to try and re-adjust to angle " + Robot.angle + ".");
			}			

			//System.out.println("Right is driving at " + rightError + " to " + right + ", and is at " + rightEncoder.getRaw() + ".");
			//System.out.println("Left is driving at " + leftError + " to " + left + ", and is at " + leftEncoder.getRaw() + ".");
			
			right1.set(-rightError);
			right2.set(-rightError);
			left1.set(leftError);
			left2.set(leftError);

			if (((rightError + leftError) / 2) < min) {
				if (angleError != 0) {
					gyroTurn(Robot.angle, true);
				}
				exit = true;
			}
		}
		System.out.println("Encoder drive finished!");	
	}
	
	/**
	 * Gyro turn. PI loop that turns the robot to angle.
	 **/
	public void gyroTurn(double angle, boolean adjustment) {
		double p;
		boolean exit = false;
		
		double kP = SmartDashboard.getNumber("gyroKP", gyroKP); //Tuning variable for proportional term.
		double kI = SmartDashboard.getNumber("gyroKI", gyroKI); 
		double kI2 = SmartDashboard.getNumber("gyroKI2", gyroKI2);
		double min = SmartDashboard.getNumber("gyroMin", gyroMin); //The minimum speed we want to move before saying we're arrived.
		double max = SmartDashboard.getNumber("gyroMax", gyroMax); //The maximum speed we want to move before capping the speed.
		double i = 0;
		boolean errorIsPositive;
		boolean errorWasPositive = true;
		
		System.out.println("Got a command to turn to '" + angle + "' degrees.");
		while(!exit && Robot.self.isAutonomous() && Robot.self.isEnabled()) {
			double error
			= angle - gyro.getAngle();
			
			p = error;
			
			if(error >= 0) errorIsPositive = true; else errorIsPositive = false;
			
			if(errorIsPositive != errorWasPositive) i = 0;
			
			errorWasPositive = errorIsPositive;
			
			i += error;
			
			double motorValue = Util.minMax((p * kP + (i * kI)), min, max);
			if(Math.abs(gyro.getRate()) < 5) motorValue += Util.minMax(i*kI2, 0, 0.1);
			
			//System.out.println("Going " + motorValue + " to " + angle + " degrees, and is at " + gyro.getAngle() + iString + ", I is " + (kI*i));
			
			right1.set(-motorValue);
			right2.set(-motorValue);
			left1.set(-motorValue);
			left2.set(-motorValue);

			if(Math.abs(error) < 2) exit = true;
		}
		if (!adjustment) {
			Robot.angle = angle;
		}
		System.out.println("Gyro turn finished!");	
	}	

	public boolean adjustToTote() {
		double adjustSpeed = 0.4;
		Robot.autonomous.add(new String[]{"adjustToTote"});		
		if (Robot.intake.leftTouchSensor.get() && !Robot.intake.rightTouchSensor.get()) {
			tankDrive(-adjustSpeed, adjustSpeed);
		} else if (Robot.intake.rightTouchSensor.get() && !Robot.intake.leftTouchSensor.get()) {
			tankDrive(adjustSpeed, -adjustSpeed);
		} else {
			return true;
		}
		return false;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveCommand());
	}
}
