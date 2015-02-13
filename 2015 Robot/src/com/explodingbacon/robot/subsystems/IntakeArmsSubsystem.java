package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.State;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Util;
import com.explodingbacon.robot.commands.IntakeArmsControlCommand;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class IntakeArmsSubsystem extends Subsystem {
	
	AnalogPotentiometer leftPot = new AnalogPotentiometer(RobotMap.leftArmPivotPot, 1, 0);
	AnalogPotentiometer rightPot = new AnalogPotentiometer(RobotMap.rightArmPivotPot, 1, 0);
	
	Talon leftArm = new Talon(RobotMap.leftArmPivotTalon);
	Talon rightArm = new Talon(RobotMap.rightArmPivotTalon);
	
	double min = 0.2;
    double max = 0.7;
    double kP = 0.5;
    double kI = 0.05;
    double kI2 = 0.0005;
    
    public double leftTarget = 0;
    public double rightTarget = 0;
    
    public void setArm(Arm arm, State state) {
    	if (arm == Arm.LEFT) {
    		leftTarget = state == State.OPEN ? 1 : 0;
    	} else if (arm == Arm.RIGHT) {
    		rightTarget = state == State.OPEN ? 1 : 0;
    	}
    	Robot.autonomous.add(new String[]{"intakeArm", arm.toString(), state.toString()});
    }
    
    public void setArms(State state) {
    	setArm(Arm.LEFT, state);
    	setArm(Arm.RIGHT, state);
    }
    
    public IntakeArmsSubsystem() {
    	if (Robot.self.isTest()) {
			SmartDashboard.putNumber("liftKP", kP);
			SmartDashboard.putNumber("liftKI", kI);
			SmartDashboard.putNumber("liftKI2", kI2);
			SmartDashboard.putNumber("liftMin", min);
			SmartDashboard.putNumber("liftMax", max);
		}
    }
	
	public void absolutePivot() {
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
    	setDefaultCommand(new IntakeArmsControlCommand());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public enum Arm {
    	LEFT,
    	RIGHT
    }
}

