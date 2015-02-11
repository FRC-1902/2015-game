package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.OI;
import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
	public Talon leftArm = new Talon(RobotMap.leftArmPivotTalon);
	public Talon rightArm = new Talon(RobotMap.rightArmPivotTalon);
    public Talon leftIntake = new Talon(RobotMap.leftIntakeTalon);
    public Talon rightIntake = new Talon(RobotMap.rightIntakeTalon);
    public TalonSRX roller = new TalonSRX(RobotMap.rollerTalon);
	public DigitalInput leftTouchSensor = new DigitalInput(RobotMap.leftTouchSensor);
	public DigitalInput rightTouchSensor = new DigitalInput(RobotMap.rightTouchSensor);
	public DigitalInput chuteTouchSensor = new DigitalInput(RobotMap.chuteTouchSensor);
	public Compressor compressor = new Compressor();
    public boolean motorStatus = false;
    public boolean reverse = false;
    public boolean rotate = false;

	public void setMotors(boolean status) {
		if (status == false) {
			leftIntake.set(0);
			rightIntake.set(0);
		} else {
			if (rotate) {
				leftIntake.set(1);
				rightIntake.set(1);
			} else {
				if (reverse) {
					leftIntake.set(1);
					rightIntake.set(-1);
				} else {
					leftIntake.set(-1);
					rightIntake.set(1);
				}
			}
		}
    	motorStatus = status;
    	Robot.autonomous.add(new String[]{"intakeMotor", status + ""});
    }
    
    public void setReversed(boolean status) {
    	reverse = status;
    	if (leftIntake.get() != 0) {
    		setMotors(true);
    	}
    	Robot.autonomous.add(new String[]{"reverseIntake", status + ""});    	
    }
    
    public void setRotated(boolean status) {
    	rotate = status;
    	if (leftIntake.get() != 0) {
    		setMotors(true);
    	}
    	Robot.autonomous.add(new String[]{"rotateIntake", status + ""});   	
    }
    
    public boolean hasTote() {
    	if (leftTouchSensor.get() && rightTouchSensor.get()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void initDefaultCommand() {
    }
}
