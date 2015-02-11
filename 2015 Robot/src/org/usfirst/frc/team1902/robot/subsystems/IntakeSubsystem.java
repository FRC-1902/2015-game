package org.usfirst.frc.team1902.robot.subsystems;

import org.usfirst.frc.team1902.robot.OI;
import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
	public Talon leftArm = new Talon(RobotMap.leftArmTalon);
	public Talon rightArm = new Talon(RobotMap.rightArmTalon);
    public Talon leftIntake = new Talon(RobotMap.leftIntakeTalon);
    public Talon rightIntake = new Talon(RobotMap.rightIntakeTalon);
    public TalonSRX roller = new TalonSRX(RobotMap.rollerTalon);
	public DigitalInput leftTouchSensor = new DigitalInput(RobotMap.leftTouchSensor);
	public DigitalInput rightTouchSensor = new DigitalInput(RobotMap.rightTouchSensor);
	public Compressor compressor = new Compressor();
    public Solenoid arms = new Solenoid(0); //TODO remove this for talon arms
    public boolean motorStatus = false;
    public boolean reverse = false;
    public boolean rotate = false;

	public void setMotors(boolean status) {
		double power = OI.action.getZ();
		if (status == false) {
			leftIntake.set(0);
			rightIntake.set(0);
		} else {
			if (rotate) {
				leftIntake.set(power);
				rightIntake.set(power);
			} else {
				if (reverse) {
					leftIntake.set(power);
					rightIntake.set(-power);
				} else {
					leftIntake.set(-power);
					rightIntake.set(power);
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
    
    public void setArms(boolean status) {
    	arms.set(status);
    	Robot.autonomous.add(new String[]{"intakeArms", status + ""});
    }
    
    public void initDefaultCommand() {
    }
}

