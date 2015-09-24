package com.explodingbacon.robot.subsystems;

import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.commands.IntakeControlCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeSubsystem extends Subsystem {
    
    public Talon leftIntake = new Talon(RobotMap.leftIntakeTalon);
    public Talon rightIntake = new Talon(RobotMap.rightIntakeTalon);
    public Solenoid arms = new Solenoid(RobotMap.intakeArmsSolenoid);
    //public Relay roller = new Relay(RobotMap.rollerRelay);
    
    public CANTalon tempRoller = new CANTalon(0x01);
    
	public DigitalInput chuteTouchSensor = new DigitalInput(RobotMap.chuteTouchSensor);
	public Compressor compressor = new Compressor();
	public double motorSpeed = 0;
	public boolean control = true;
	
    public void setMotors(double d) {
    	leftIntake.set(d);
    	rightIntake.set(-d);
    	motorSpeed = d;
    }
   
    public void initDefaultCommand() {
    	setDefaultCommand(new IntakeControlCommand());
    }
}

