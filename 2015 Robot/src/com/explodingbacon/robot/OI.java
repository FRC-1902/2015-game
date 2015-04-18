package com.explodingbacon.robot;

import com.explodingbacon.robot.commands.LiftPistonCommand;
import com.explodingbacon.robot.commands.RollerToggleCommand;
import com.explodingbacon.robot.commands.ToucanToggleCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick left = new Joystick(0);
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
	public Button liftDownFast;
	public Button toucans;
	public Button liftWithDrive;
	
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
		liftDownFast = xbox.rightJoyButton;
		toucans = new JoystickButton(left, 2);
		liftWithDrive = new JoystickButton(left, 6);
				
		//======================================================		
		
		toggleRoller.whenPressed(new RollerToggleCommand());
		
		liftPiston.whenPressed(new LiftPistonCommand());
		
		toucans.whenPressed(new ToucanToggleCommand());
	}
	
	public OI() {
		init();
	}
}