package org.usfirst.frc.team1902.robot;

import org.usfirst.frc.team1902.robot.commands.DriveTypeToggleCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleArmsCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleReverseCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleRotateCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleCommand;
import org.usfirst.frc.team1902.robot.commands.LiftCommand;
import org.usfirst.frc.team1902.robot.commands.RecordAutonomousToggleCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public static Joystick left = new Joystick(0);
	public static Joystick right = new Joystick(1);
	public static Joystick three = new Joystick(2);
	public static Joystick action = null;
	
	Button driveToggle;
	Button intake;
	Button reverseIntake;
	Button rotateIntake;
	Button intakeArms;
	Button canGrabber;
	Button liftUp;
	Button liftDown;
	Button pushTote;
	Button recordAutonomous;		
	
	//Anything with the value of 9001 is a placeholder.
	public void init() {
		//Use "action = three;" if you actually have three joysticks available
		action = right;
		//Define buttons
		driveToggle = new JoystickButton(action, 8);
		intake = new JoystickButton(action, 4);
		reverseIntake = new JoystickButton(action, 5);
		rotateIntake = new JoystickButton(action, 9001);
		intakeArms = new JoystickButton(action, 1);
		canGrabber = new JoystickButton(action, 9001);
		liftUp = new JoystickButton(action, 3);
		liftDown = new JoystickButton(action, 2);
		pushTote = new JoystickButton(action, 9001);
		recordAutonomous = new JoystickButton(action, 9);
		
		//Define what happens when the buttons are pressed
		driveToggle.whenPressed(new DriveTypeToggleCommand());
		
		intake.whenPressed(new IntakeToggleCommand());
		
		reverseIntake.whenPressed(new IntakeToggleReverseCommand(true));
		reverseIntake.whenReleased(new IntakeToggleReverseCommand(false));
		
		rotateIntake.whenPressed(new IntakeToggleRotateCommand(true));
		rotateIntake.whenReleased(new IntakeToggleRotateCommand(false));
		
		intakeArms.whenPressed(new IntakeToggleArmsCommand(false));
		intakeArms.whenReleased(new IntakeToggleArmsCommand(true));
		
		//canGrabber.whenPressed(new CanGrabberToggleCommand());
		
		liftUp.whenPressed(new LiftCommand(1));
		liftUp.whenReleased(new LiftCommand(0));
		
		liftDown.whenPressed(new LiftCommand(-1));
		liftDown.whenReleased(new LiftCommand(0));
		
		//pushTote.whenPressed(new PushToteCommand());
		
		recordAutonomous.whenPressed(new RecordAutonomousToggleCommand());
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