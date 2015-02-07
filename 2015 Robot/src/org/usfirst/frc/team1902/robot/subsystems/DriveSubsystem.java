package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

	//public Talon left1 = new Talon(RobotMap.leftDriveTalon1);
	public Talon left1 = new Talon(1);
	//public Talon left2 = new Talon(RobotMap.leftDriveTalon2);
	public Talon right1 = new Talon(0);
	//public Talon right1 = new Talon(RobotMap.rightDriveTalon1);
	//public Talon right2 = new Talon(RobotMap.rightDriveTalon2);
	public Encoder leftEncoder = new Encoder(1, 2, true);
	public Encoder rightEncoder = new Encoder(3, 4);
	public Gyro gyro = new Gyro(0);
	//public DigitalInput leftSensor = new DigitalInput(0);
	//public DigitalInput rightSensor = new DigitalInput(1);
	public int encoderSkip = 0;
	public boolean arcadeDrive = true;
	
	public DriveSubsystem() {
		leftEncoder.setDistancePerPulse(1);
		rightEncoder.setDistancePerPulse(1);
		leftEncoder.reset();
		rightEncoder.reset();		
		gyro.reset();
	}

	//Tank drive. Uses left and right inputs ranging from -1 to 1 to move the robot.
	public void tankDrive(double left, double right) {
		this.left1.set(-left);
		//this.left2.set(left);
		this.right1.set(right);
		//this.right2.set(right);
		//System.out.println("Left encoder: " + leftEncoder.getRaw());
		//System.out.println("Right encoder: " + rightEncoder.getRaw());
		//encoderSkip++;
		//if (encoderSkip == 10) {
			Robot.autonomous.add(new String[]{"drive", leftEncoder.getRaw() + "", rightEncoder.getRaw()  + "", gyro.getAngle() + ""});
			if (Robot.autonomous.recording) {
				leftEncoder.reset();
				rightEncoder.reset();
			}
			encoderSkip = 0;
		//}
	}

	//Arcade drive. Uses a single joystick to move the robot.
	public void arcadeDrive(Joystick joy) {
		tankDrive(joy.getY() - joy.getX(), joy.getY() + joy.getX());
	}

	//Encoder drive. Takes a left and right encoder value and plugs them into a P loop.
	public void encoderDrive(double left, double right, double nextLeft, double nextRight) {
		gyroTurn(Robot.angle, true);
		leftEncoder.reset();
		rightEncoder.reset();
		double rightError, leftError, angleError;
		boolean exit = false;

		double kP = 0.01; //Tuning variable for the proportional term
		double kAngleP = 0.15; //Tuning variable for the proportional term of heading correction
		double min = 0.3; //The minimum speed we want to move before saying we're arrived.
		double max = 0.5; //The maximum speed we want to move before capping the speed.
		double leftSign = -1;
		double rightSign = -1;
		double lastCorrectLeft = 0;
		double lastCorrectRight = 0;
		
		rightError = right - rightEncoder.getRaw();
		leftError  = left - leftEncoder.getRaw();		
		
		if (rightError >= 0) {
			rightSign = 1;
		}
		if (leftError >= 0) {
			leftSign = 1;
		}
		
		System.out.println("Got a command to drive to '" + left + "' and '" + right + "'.");
		while(!exit && Robot.self.isAutonomous() && Robot.self.isEnabled()) {
			rightError = right - rightEncoder.getRaw();
			leftError  = left - leftEncoder.getRaw();
			angleError = Robot.angle - gyro.getAngle();

			rightError *= kP;
			leftError *= kP;
			angleError *=  kAngleP;
			
			rightError = minMax(rightError, min, max);
			leftError = minMax(leftError, min, max);
			System.out.println("Gyro: " + gyro.getAngle() + ", AngleError: " + angleError);
			
			if (nextLeft != 0 && nextRight != 0) {
				if (Robot.sign(rightError) != rightSign && Robot.sign(leftError) != leftSign) {
					if (nextRight - Robot.drive.rightEncoder.getRaw() < nextRight - lastCorrectRight && nextLeft - Robot.drive.leftEncoder.getRaw() > nextLeft - lastCorrectLeft) {
						rightError = 0;
						leftError = 0;
					}
				} else if (Robot.sign(rightError) != rightSign) {
					lastCorrectRight = Robot.drive.rightEncoder.getRaw();
				} else {
					lastCorrectLeft = Robot.drive.leftEncoder.getRaw();
				}
			}
			
			if (Math.abs(angleError) > 1) { //angleError is in degrees right now
				angleError = minMax(angleError * kAngleP, 0, max);
				leftError -= angleError;
				rightError += angleError;
				System.out.println("Adjusted driving by " + angleError + " to try and re-adjust to angle " + Robot.angle + ".");
			}			

			System.out.println("Right is driving at " + rightError + " to " + right + ", and is at " + rightEncoder.getRaw() + ".");
			System.out.println("Left is driving at " + leftError + " to " + left + ", and is at " + leftEncoder.getRaw() + ".");
			
			right1.set(-rightError);
			left1.set(leftError);

			if (((rightError + leftError) / 2) < min) {
				if (angleError != 0) {
					gyroTurn(Robot.angle, true);
				}
				exit = true;
			}
		}
		System.out.println("Encoder drive finished!");	
	}
	
	//Turns to X degrees.
	public void gyroTurn(double angle, boolean adjustment) {
		double p;
		boolean exit = false;
		
		double kP = 0.02; //Tuning variable for proportional term.
		double kI = 0.00025; //0.0005
		double kI2 = 0.000025;
		double min = 0.2; //The minimum speed we want to move before saying we're arrived.
		double max = 0.4; //The maximum speed we want to move before capping the speed.
		double i = 0;
		boolean errorIsPositive;
		boolean errorWasPositive = true;
		String iString = ", accelerating";
		
		System.out.println("Got a command to turn to '" + angle + "' degrees.");
		while(!exit && Robot.self.isAutonomous() && Robot.self.isEnabled()) {
			double error = angle - gyro.getAngle();
			
			p = error;
			
			if(error >= 0) errorIsPositive = true; else errorIsPositive = false;
			
			if(errorIsPositive != errorWasPositive) i = 0;
			
			errorWasPositive = errorIsPositive;
			
			i += error;
			/*
			if (Math.abs(p) < min) {
				double angleDiff = angle - gyro.getAngle();
				if (Math.abs(angleDiff) > 2) {
					p = min * Robot.sign(angleDiff);
				} else {
					p = 0;
				}
			}
			
			if (Math.abs(gyro.getRate()) > Math.abs(5)) {
				kI = 0;
				iString = ".";
			}
			*/
			double motorValue = minMax((p * kP + (i * kI)), min, max);
			if(Math.abs(gyro.getRate()) < 5) motorValue += minMax(i*kI2, 0, 0.1);
			
			System.out.println("Going " + motorValue + " to " + angle + " degrees, and is at " + gyro.getAngle() + iString + ", I is " + (kI*i));
			
			right1.set(-motorValue);
			left1.set(-motorValue);

			if(Math.abs(error) < 2) exit = true;
		}
		if (!adjustment) {
			Robot.angle = angle;
		}
		System.out.println("Gyro turn finished!");	
	}
	
	public double minMax(double d, double min, double max) {
		double minMaxed = d;
		if (Math.abs(d) >= Math.abs(max)) {
			minMaxed = max * Robot.sign(d);
		} else if (Math.abs(d) < Math.abs(min)) {
			minMaxed = 0;
		}
		return minMaxed;
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