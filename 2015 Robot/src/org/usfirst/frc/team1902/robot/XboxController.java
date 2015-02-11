package org.usfirst.frc.team1902.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController extends Joystick {

	public Button a;
	public Button b;
	public Button x;
	public Button y;
	public Button start; //the left one
	public Button select;
	public Button leftBumper;
	public Button rightBumper;
	public Button leftJoyButton;
	public Button rightJoyButton;
	
	public XboxController(int port) {
		super(port);
		a = new JoystickButton(this, 1);
		b = new JoystickButton(this, 2);
		x = new JoystickButton(this, 3);
		y = new JoystickButton(this, 4);
		start = new JoystickButton(this, 7);
		select = new JoystickButton(this, 8);
		leftBumper = new JoystickButton(this, 5);
		rightBumper = new JoystickButton(this, 6);
		leftJoyButton = new JoystickButton(this, 9);
		rightJoyButton = new JoystickButton(this, 10);
	}
	
	public double getX2() {
		return getRawAxis(4);
	}
	
	public double getY2() {
		return getRawAxis(5);
	}
	
	public double getDPad() {
		return getPOV(0);
	}
	
	public double getLeftTrigger() {
		return getRawAxis(2);
	}
	
	public double getRightTrigger() {
		return getRawAxis(3);
	}

}
