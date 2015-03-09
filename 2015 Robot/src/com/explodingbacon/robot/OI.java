package com.explodingbacon.robot;

import com.explodingbacon.robot.commands.WingsToggleCommand;
import com.explodingbacon.robot.commands.RollerToggleCommand;

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
	public Button wings;
	public Button toggleRoller;
	public Button toteStack;
	public Button doToteStack;
	public Button liftScoring;
	
	public void init() {
		driveSlow = new JoystickButton(left, 11);
		intake = xbox.leftBumper;
		reversedIntake = xbox.rightBumper;
		intakeArms = xbox.x;
		wings = xbox.y;
		toggleRoller = xbox.b;
		toteStack = xbox.start;
		doToteStack = xbox.select;
		liftScoring = xbox.a;
		
		//======================================================		
		
		wings.whenPressed(new WingsToggleCommand());		

		toggleRoller.whenPressed(new RollerToggleCommand());
	}
	
	public OI() {
		init();
	}
}