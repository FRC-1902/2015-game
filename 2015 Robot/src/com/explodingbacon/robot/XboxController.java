package com.explodingbacon.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController extends Joystick implements TimerUser {

	public Button a;
	public Button b;
	public Button x;
	public Button y;
	public Button start;
	public Button select;
	public Button leftBumper;
	public Button rightBumper;
	public Button leftJoyButton;
	public Button rightJoyButton;
	public float rumbleL = 0;
	public float rumbleR = 0;
	public Timer rumbleTimer;
	
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
	
	/**
	 * Gets the X axis of the right Xbox joystick.
	 * @return The X axis of the right Xbox joystick.
	 */
	public double getX2() {
		return getRawAxis(4);
	}
	
	/**
	 * Gets the Y axis of the right Xbox joystick.
	 * @return The Y axis of the right Xbox joystick.
	 */
	public double getY2() {
		return getRawAxis(5);
	}
	
	/**
	 * See the documentation for Joystick.getPOV(int).
	 */
	public double getDPad() { //Possibly read axises 5 and 6 for X and Y
		return getPOV(0);
	}
	
	/**
	 * Gets the value of the left trigger.
	 * @return The value of the left trigger.
	 */
	public double getLeftTrigger() {
		return getRawAxis(2);
	}
	
	/**
	 * Gets the value of the right trigger.
	 * @return The value of the right trigger.
	 */
	public double getRightTrigger() {
		return getRawAxis(3);
	}
	
	/**
	 * Sets the rumble value of the left and right rumble motors.
	 * @param l The left rumble value.
	 * @param r The right rumble value.
	 */
	public void rumble(float l, float r) {
		rumbleL = l;
		rumbleR = r;
		setRumble(RumbleType.kLeftRumble, l);
		setRumble(RumbleType.kRightRumble, r);
	}
	
	/**
	 * Makes the controller rumble for X seconds.
	 * @param l The left rumble value.
	 * @param r The right rumble value.
	 * @param time How long the controller should rumble.
	 */
	public void rumble(float l, float r, double time) {
		rumble(l, r);
		rumbleTimer = new Timer(0.5, false, this).begin();
	}

	@Override
	public void timerBegin() {}

	@Override
	public void timer() {
		rumble(0, 0);
	}

	@Override
	public void timerHalt() {
		rumbleTimer = null;
	}
}
