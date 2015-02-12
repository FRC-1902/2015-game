package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
	public Talon leftArm = new Talon(RobotMap.leftArmPivotTalon);
	public Talon rightArm = new Talon(RobotMap.rightArmPivotTalon);
    public Talon leftIntake = new Talon(RobotMap.leftIntakeTalon);
    public Talon rightIntake = new Talon(RobotMap.rightIntakeTalon);
    public TalonSRX roller = new TalonSRX(RobotMap.rollerTalon);
	public DigitalInput chuteTouchSensor = new DigitalInput(RobotMap.chuteTouchSensor);
	public double motorSpeed = 0;

    public void setMotors(double d) {
    	leftIntake.set(d);
    	rightIntake.set(d);
    	motorSpeed = d;
    	Robot.autonomous.add(new String[]{"intakeMotor", d + ""});
    }
    
    public void setRoller(boolean status) {
    	if (status) {
    		roller.set(1);
    	} else {
    		roller.set(0);
    	}
    	Robot.autonomous.add(new String[]{"roller", status + ""});    	
    }
    
    public void initDefaultCommand() {
    }
}

