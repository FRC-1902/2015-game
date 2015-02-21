package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {
    
    public Talon leftIntake = new Talon(RobotMap.leftIntakeTalon);
    public Talon rightIntake = new Talon(RobotMap.rightIntakeTalon);
    public Solenoid arms = new Solenoid(RobotMap.intakeArmsSolenoid);
    public Relay roller = new Relay(RobotMap.rollerRelay);
	public DigitalInput chuteTouchSensor = new DigitalInput(RobotMap.chuteTouchSensor);
	public double motorSpeed = 0;

	public IntakeSubsystem() {
		roller.setDirection(Direction.kForward);
	}
	
    public void setMotors(double d) {
    	leftIntake.set(d);
    	rightIntake.set(-d);
    	motorSpeed = d;
    	Robot.autonomous.add(new String[]{"intakeMotor", d + ""});
    }
    
    public void setRoller(boolean status) {
    	if (status) {
    		roller.set(Value.kOn);
    	} else {
    		roller.set(Value.kOff);
    	}
    	Robot.autonomous.add(new String[]{"roller", status + ""});    	
    }
    
    public void initDefaultCommand() {
    }
}

