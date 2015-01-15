 package org.usfirst.frc.team1902.robot;

import org.usfirst.frc.team1902.robot.commands.CanGrabberToggleCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeArmsToggleCommand;
import org.usfirst.frc.team1902.robot.commands.IntakeToggleCommand;
import org.usfirst.frc.team1902.robot.commands.RecordAutonomousToggleCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	//ANYTHING with the value 9001 is a placeholder ID
	public static Joystick left = new Joystick(0);
	public static Joystick right = new Joystick(1);
	
	Button intake = new JoystickButton(right, 9001);
	Button intakeArms = new JoystickButton(right, 9001);
	Button canGrabber = new JoystickButton(right, 9001);
	Button recordAutonomous = new JoystickButton(right, 9001);
	
	public OI() {
		intake.whenPressed(new IntakeToggleCommand(true));
		intake.whenReleased(new IntakeToggleCommand(false));
		intakeArms.whenPressed(new IntakeArmsToggleCommand());
		canGrabber.whenPressed(new CanGrabberToggleCommand());
		recordAutonomous.whenPressed(new RecordAutonomousToggleCommand());
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

