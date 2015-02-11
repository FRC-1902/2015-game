package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.RobotMap;
import org.usfirst.frc.team1902.robot.Util;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class IntakeArmsSubsystem extends Subsystem {
	
	AnalogPotentiometer leftPot = new AnalogPotentiometer(RobotMap.leftArmPivotPot);
	AnalogPotentiometer rightPot = new AnalogPotentiometer(RobotMap.rightArmPivotPot);
	
	Talon leftArm = new Talon(RobotMap.leftArmPivotTalon);
	Talon rightArm = new Talon(RobotMap.rightArmPivotTalon);
	
	double min = 0.2;
    double max = 0.7;
    double kP = 0.5;
    double kI = 0.05;
    double kI2 = 0.0005;
    
    double leftTarget = 0;
    double rightTarget = 0;
    
    public void getTargets()
    {
    	//leftTarget = something
    	//rightTarget = something
    }
	
	public void absolutePivot() 
	{
		double leftError, rightError, leftP, rightP, leftI, rightI, leftSetpoint, rightSetpoint;
		leftI = 0;
		rightI = 0;

		kP = SmartDashboard.getNumber("armsKP", kP);
		kI = SmartDashboard.getNumber("armsKI", kI);
		kI2 = SmartDashboard.getNumber("armsKI2", kI2);
		min = SmartDashboard.getNumber("armsMin", min);
		max = SmartDashboard.getNumber("armsMax", max);

		do {
			leftError = leftTarget - leftPot.get();
			rightError = rightTarget - rightPot.get();

			leftP = leftError;
			rightP = rightError;

			leftI += leftError;
			rightI += rightError;

			leftSetpoint = Util.minMax(leftP * kP + leftI * kI, min, max);
			leftSetpoint += leftI * kI2;
			
			rightSetpoint = Util.minMax(rightP * kP + rightI * kI, min, max);
			rightSetpoint += rightI * kI2;

			leftArm.set(leftSetpoint);
			rightArm.set(rightSetpoint);
		} while (!"pigs".equals("fly"));
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

