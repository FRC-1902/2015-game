package com.explodingbacon.robot;

import com.explodingbacon.robot.Robot.ControlType;
import com.explodingbacon.robot.commands.QuickCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick left;
	public static Joystick right; //Only used when in tank drive mode
	public static XboxController xbox;
	
	public Button driveSlow;
	public Button intake;
	public Button reversedIntake;
	public Button intakeArms;
	public Button toggleRoller;
	public Button toteStack;
	public Button doToteStack;
	public Button liftScoring;
	public Button liftPiston;
	public Button liftWithDrive;
	
	/**
	 * Initializes all the buttons and their actions.
	 */
	public void initControls() {
		left = new Joystick(0);
		if (Robot.arcadeDrive) {
			right = null;
			xbox = new XboxController(1);
		} else {			
			right = new Joystick(1);
			xbox = new XboxController(2);
		}
		
		InternalButton none = new InternalButton(); //Effectively a button that is eternally not-pressed
		
		//TODO revise how the different control schemes work based off of input from team members	
		driveSlow = new JoystickButton(left, 11);
		intake = xbox.leftBumper;
		reversedIntake = xbox.rightBumper;
		intakeArms = xbox.x;
		liftScoring = xbox.a;
		
		if (Robot.controlType == ControlType.COMPLEX) {
			liftWithDrive = new JoystickButton(left, 6);
		} else {
			liftWithDrive = none;
		}
		
		if (Robot.controlType != ControlType.SIMPLE) {
			toteStack = xbox.start;
			doToteStack = xbox.select;
			toggleRoller = xbox.b;
		} else {
			toteStack = none;
			doToteStack = none;
			toggleRoller = none;
		}
		liftPiston = new JoystickButton(left, 8);

		//======================================================		
		
		toggleRoller.whenPressed(new QuickCommand() {
			protected void initialize() {
				Robot.intake.setRoller(Robot.intake.roller.get() == Value.kOn ? false : true);
			}
		});
		
		//TODO Double check this works. It has supposedly worked in the past, but it looks like it shouldn't.
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
	 * ==TYPE 1: COMPLEX==
	 * 
	 * Complex is identical to Pork Lift's competition season controls: almost everything on your Xbox controller is mapped to something. It's suggested to only use this control
	 * type if you have experience as a driver or have heavily used this control type already.
	 * 
	 * 
	 * ==TYPE 2: NORMAL==
	 * 
	 * Normal currently is almost identical to Complex because I haven't gotten to talk to other team members about how they want this control scheme to work.
	 * The only different currently is that the "liftWithDrive" button, which is a mostly untested and experimental feature, is disabled.
	 * 
	 * 
	 * ==TYPE 3: SIMPLE==
	 * 
	 * Simple is designed to be used by brand new members or guest drivers. Access to the automatic tote stacking buttons, the roller, and liftWithDrive have been disabled.	
	 * In addition, control of the lift has been simplified to manual control via the upper-left joystick on the XBox controller.
	 * 
	 */
}