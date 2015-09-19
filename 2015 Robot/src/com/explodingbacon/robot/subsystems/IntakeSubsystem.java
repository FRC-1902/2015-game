package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.commands.IntakeControlCommand;
import edu.wpi.first.wpilibj.Compressor;
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
	public Compressor compressor = new Compressor();
	public double motorSpeed = 0;
	public boolean control = true;

	public IntakeSubsystem() {
		roller.setDirection(Direction.kForward);
	}
	
    public void setMotors(double d) {
    	leftIntake.set(d);
    	rightIntake.set(-d);
    	motorSpeed = d;
    }
    
    public boolean getRoller() {
    	if (roller.get() == Value.kOn) return true;
    	return false;
    }
    
    public void setRoller(boolean status) {
    	roller.set(status ? Value.kOn : Value.kOff);
    }
   
    public void initDefaultCommand() {
    	setDefaultCommand(new IntakeControlCommand());
    }
}

