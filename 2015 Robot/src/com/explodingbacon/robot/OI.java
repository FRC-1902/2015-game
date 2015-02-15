package com.explodingbacon.robot;

import com.explodingbacon.robot.Robot.State;
import com.explodingbacon.robot.commands.BinGrabberToggleCommand;
import com.explodingbacon.robot.commands.HumanPlayerStackCommand;
import com.explodingbacon.robot.commands.IntakeToggleCommand;
import com.explodingbacon.robot.commands.RollerToggleCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class OI {
	public static Joystick left = new Joystick(0);
	public static XboxController xbox = new XboxController(1);
	
	Button intake;
	Button reversedIntake;
	Button binGrabber;
	Button toggleRoller;
	Button humanPlayerStack;
	
	public void init() {
		intake = xbox.leftBumper;
		reversedIntake = xbox.rightBumper;
		binGrabber = xbox.y;
		toggleRoller = xbox.b;
		humanPlayerStack = xbox.start;
		
		//======================================================
		
		intake.whenPressed(new IntakeToggleCommand());
		
		reversedIntake.whenPressed(new IntakeToggleCommand(State.BACKWARDS));
		
		binGrabber.whenPressed(new BinGrabberToggleCommand());		

		toggleRoller.whenPressed(new RollerToggleCommand());
		
		humanPlayerStack.whileHeld(new HumanPlayerStackCommand());
	}
	
	public OI() {
		init();
	}
}