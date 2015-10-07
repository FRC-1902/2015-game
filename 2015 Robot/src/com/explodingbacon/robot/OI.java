package com.explodingbacon.robot;

import com.explodingbacon.robot.Robot.ControlType;
import com.explodingbacon.robot.commands.QuickCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class OI {
	public static Joystick left;
	public static Joystick right; //Only used when in tank drive mode
	public static XboxController xbox;
	
	public Button driveSlow;
	public Button intake;
	public Button reversedIntake;
	public Button intakeArms;
	public Button toteStack;
	public Button doToteStack;
	public Button liftScoring;
	public Button liftPiston;
	public Button toteStop;
	public Button roller;
	
	/**
	 * Initializes all the buttons and their actions.
	 */
	public void initControls() {
		//Xbox controller is always 0, left is 1, right is 2
		xbox = new XboxController(0);
		if (Robot.controlType == ControlType.NORMAL) {
			left = new Joystick(1);
			if (Robot.arcadeDrive) {
				right = null;
			} else {			
				right = new Joystick(2);
			}
		} else {
			left = null;
			right = null;
		}
		
		InternalButton none = new InternalButton(); //Effectively a button that is eternally not-pressed
		
		if (Robot.controlType == ControlType.NORMAL) {
			driveSlow = new JoystickButton(left, 11);
			liftPiston = new JoystickButton(left, 8);
		} else {
			driveSlow = none;
			liftPiston = none;
		}
		
		intake = none;
		reversedIntake = none;		
		intakeArms = none;
		
		toteStop = xbox.y;
			
		liftScoring = xbox.a;
		
		roller = xbox.b;
		
		toteStack = xbox.start;
		doToteStack = xbox.select;

		//======================================================		
		
		liftPiston.whenPressed(new QuickCommand() {
			protected void initialize() {
		    	Robot.lift.setPiston(true);
		    }

		    protected boolean isFinished() {
		        return !Robot.oi.liftPiston.get();
		    }

		    protected void done() {
		    	Robot.lift.setPiston(false);
		    }
		});
	}
	
	public OI() {
		initControls();
	}
	
	/*
	 * THE COMPLETE GUIDE TO CONTROL TYPES
	 * 
	 * 
	 * Control Types are selected via SmartDashboard. Once the robot is put into teleop mode, the current control type is locked in and cannot be changed until the next time
	 * it goes into teleop mode.
	 * 
	 * 
	 * ==TYPE 1: NORMAL==
	 * 
	 * The new standard way of driving Pork Lift. If you've driven him since Panther Prowl, you've learned these controls.
	 * 
	 * ==TYPE 2: SINGLEPLAYER==
	 * 
	 * Fits everything onto just the XBox controller. When not conflicting with changes necessary to fit everything onto one joystick, this uses Normal's controls. Do NOT use at competitions.
	 */
}