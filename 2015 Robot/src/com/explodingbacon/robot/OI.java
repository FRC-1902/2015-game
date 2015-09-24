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
	public Button toggleRoller;
	public Button toteStack;
	public Button doToteStack;
	public Button liftScoring;
	public Button liftPiston;
	public Button liftWithDrive;
	public Button toteStop1;
	public Button toteStop2;
	
	/**
	 * Initializes all the buttons and their actions.
	 */
	public void initControls() {
		xbox = new XboxController(0);
		if (Robot.controlType != ControlType.SINGLEPLAYER) {
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
		
		if (Robot.controlType != ControlType.SINGLEPLAYER) {
			driveSlow = new JoystickButton(left, 11);
			liftPiston = new JoystickButton(left, 8);
		} else {
			driveSlow = none;
			liftPiston = none;
		}
		
		intake = none;
		reversedIntake = none;		
		intakeArms = none;
		
		
		if (Robot.controlType == ControlType.COMPLEX) {
			liftWithDrive = new JoystickButton(left, 6);
			liftScoring = xbox.a;
			toteStop1 = xbox.rightBumper;
			toteStop2 = xbox.leftBumper;
		} else {
			liftWithDrive = none;
			liftScoring = none;
			toteStop1 = xbox.y;
			toteStop2 = none;
		}
		
		toteStack = xbox.start;
		doToteStack = xbox.select;
		toggleRoller = xbox.b;

		//======================================================		
		
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
		
		Command toteStopCommand = new QuickCommand() {
			@Override
			protected void initialize() {
				Robot.toteStop.set(false);
			}
			
			@Override
			protected boolean isFinished() {
				return !toteStop1.get() && !toteStop2.get();
			}
			
			@Override
			protected void done() {
				Robot.toteStop.set(true);
			}
		};

		toteStop1.whenActive(toteStopCommand);
		toteStop2.whenActive(toteStopCommand);
		
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
	 * Normal changes the Complex controls a bit. You can move the lift to scoring position via the left and right DPad buttons, tote stops are now controlled by the Y button,
	 * and the drawer slides are now controlled by the right Xbox joystick.
	 * 
	 * ==TYPE 3: SINGLEPLAYER==
	 * 
	 * Fits everything onto just the XBox controller. When not conflicting with changes necessary to fit everything onto one joystick, this uses Normal's controls. Do NOT use at competitions.
	 */
}