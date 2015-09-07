package com.explodingbacon.robot;

import com.explodingbacon.robot.commands.QuickCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick left = new Joystick(0);
	public static Joystick right = null; //Only used when in tank drive mode
	public static XboxController xbox = new XboxController(1);
	
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
	public void init() {
		driveSlow = new JoystickButton(left, 11);
		intake = xbox.leftBumper;
		reversedIntake = xbox.rightBumper;
		intakeArms = xbox.x;
		toggleRoller = xbox.b;
		toteStack = xbox.start;
		doToteStack = xbox.select;
		liftScoring = xbox.a;
		liftPiston = new JoystickButton(left, 8);
		liftWithDrive = new JoystickButton(left, 6);
				
		//======================================================		
		
		toggleRoller.whenPressed(new QuickCommand() {
			protected void initialize() {
				Robot.intake.setRoller(Robot.intake.roller.get() == Value.kOn ? false : true);
			}
		});
		
		//TODO Double check this works. It's supposedly worked in the past, but it looks like it shouldn't.
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
		init();
	}
}