package com.explodingbacon.robot;

import com.explodingbacon.robot.commands.AdjustToToteCommand;
import com.explodingbacon.robot.commands.BinGrabberToggleCommand;
import com.explodingbacon.robot.commands.HumanPlayerStackCommand;
import com.explodingbacon.robot.commands.IntakeToggleCommand;
import com.explodingbacon.robot.commands.IntakeToggleReverseCommand;
import com.explodingbacon.robot.commands.PushToteCommand;
import com.explodingbacon.robot.commands.RecordAutonomousToggleCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class OI {
	public static Joystick left = new Joystick(0);
	public static XboxController xbox = new XboxController(1);
	
	Button intake;
	Button reverseIntake;
	Button binGrabber;
	Button pushTote;
	Button adjustToTote;
	Button humanPlayerStack;
	Button recordAutoOn;	
	Button recordAutoOff;
	
	//Anything with the value of 9001 is a placeholder.
	public void init() {		
		//Define buttons
		intake = xbox.x;
		reverseIntake = xbox.a;
		binGrabber = xbox.y;
		pushTote = xbox.b;
		adjustToTote = xbox.leftJoyButton;
		humanPlayerStack = xbox.rightJoyButton;
		recordAutoOn = xbox.start;
		recordAutoOff = xbox.select;
		
		//Define what happens when the buttons are pressed		
		intake.whenPressed(new IntakeToggleCommand());
		
		reverseIntake.whenPressed(new IntakeToggleReverseCommand(true));
		reverseIntake.whenReleased(new IntakeToggleReverseCommand(false));
		
		binGrabber.whenPressed(new BinGrabberToggleCommand());
		
		pushTote.whenPressed(new PushToteCommand());
		
		adjustToTote.whileHeld(new AdjustToToteCommand());
		
		humanPlayerStack.whileHeld(new HumanPlayerStackCommand());
		
		recordAutoOn.whenPressed(new RecordAutonomousToggleCommand(true));
		recordAutoOff.whenPressed(new RecordAutonomousToggleCommand(false));
	}
	
	public OI() {
		init();
	}
}