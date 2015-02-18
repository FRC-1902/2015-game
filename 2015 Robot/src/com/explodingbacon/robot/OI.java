package com.explodingbacon.robot;

import com.explodingbacon.robot.Robot.State;
import com.explodingbacon.robot.commands.BinGrabberToggleCommand;
import com.explodingbacon.robot.commands.HumanPlayerStackCommand;
import com.explodingbacon.robot.commands.IntakeArmToggleCommand;
import com.explodingbacon.robot.commands.IntakeToggleCommand;
import com.explodingbacon.robot.commands.LiftCommand;
import com.explodingbacon.robot.commands.RollerToggleCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class OI {
	public static Joystick left = new Joystick(0);
	public static XboxController xbox = new XboxController(1);
	
	Button intake;
	Button intakeArms;
	Button reversedIntake;
	Button binGrabber;
	Button toggleRoller;
	Button humanPlayerStack;
	Button liftUp;
	Button liftDown;
	
	public void init() {
		intake = xbox.leftBumper;
		intakeArms = xbox.x;
		reversedIntake = xbox.rightBumper;
		binGrabber = xbox.y;
		toggleRoller = xbox.b;
		humanPlayerStack = xbox.start;
		liftUp = xbox.dpadUp;
		liftDown = xbox.dpadDown;
		
		//======================================================
		
		intake.whenPressed(new IntakeToggleCommand());
		
		intakeArms.whenPressed(new IntakeArmToggleCommand(true));
		intakeArms.whenReleased(new IntakeArmToggleCommand(false));
		
		reversedIntake.whenPressed(new IntakeToggleCommand(State.BACKWARDS));
		
		binGrabber.whenPressed(new BinGrabberToggleCommand());		

		toggleRoller.whenPressed(new RollerToggleCommand());
		
		humanPlayerStack.whileHeld(new HumanPlayerStackCommand());
		
		//liftUp.whenPressed(new LiftCommand(0.5));
		//liftUp.whenReleased(new LiftCommand(0));
		
		//liftDown.whenPressed(new LiftCommand(-0.5));
		//liftDown.whenReleased(new LiftCommand(0));
	}
	
	public OI() {
		init();
	}
}