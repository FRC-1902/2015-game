package org.usfirst.frc.team1902.robot;

import org.usfirst.frc.team1902.robot.commands.BinGrabberToggleCommand;
import org.usfirst.frc.team1902.robot.commands.HumanPlayerStackCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleReverseCommand;
import org.usfirst.frc.team1902.robot.commands.RecordAutonomousToggleCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class OI {
	public static Joystick left = new Joystick(0);
	public static XboxController xbox = new XboxController(0);
	
	Button driveToggle;
	Button intake;
	Button reverseIntake;
	Button rotateIntake;
	Button binGrabber;
	Button pushTote;
	Button adjustToTote;
	Button humanPlayerStack;
	Button recordAutoOn;	
	Button recordAutoOff;
	
	//Anything with the value of 9001 is a placeholder.
	public void init() {		
		//Define buttons
		//driveToggle = new JoystickButton(left, 8);
		intake = xbox.x;
		reverseIntake = xbox.a;
		//rotateIntake = new JoystickButton(action, 9001);
		binGrabber = xbox.y;
		//pushTote = new JoystickButton(action, 9001);
		//adjustToTote = new JoystickButton(action, 9001);
		humanPlayerStack = xbox.rightJoyButton;
		recordAutoOn = xbox.start;
		recordAutoOff = xbox.select;
		
		//Define what happens when the buttons are pressed
		//driveToggle.whenPressed(new DriveTypeToggleCommand());
		
		intake.whenPressed(new IntakeToggleCommand());
		
		reverseIntake.whenPressed(new IntakeToggleReverseCommand(true));
		reverseIntake.whenReleased(new IntakeToggleReverseCommand(false));
		
		//rotateIntake.whenPressed(new IntakeToggleRotateCommand(true));
		//rotateIntake.whenReleased(new IntakeToggleRotateCommand(false));
		
		binGrabber.whenPressed(new BinGrabberToggleCommand());
		
		//pushTote.whenPressed(new PushToteCommand());
		
		//adjustToTote.whileHeld(new AdjustToToteCommand());
		
		humanPlayerStack.whileHeld(new HumanPlayerStackCommand());
		
		recordAutoOn.whenPressed(new RecordAutonomousToggleCommand(true));
		recordAutoOff.whenPressed(new RecordAutonomousToggleCommand(false));
	}
	
	public OI() {
		init();
	}
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}