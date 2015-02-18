package com.explodingbacon.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController extends Joystick {

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
	public InternalButton dpadUp;
	public InternalButton dpadRight;
	public InternalButton dpadDown;
	public InternalButton dpadLeft;
	public float rumbleL = 0;
	public float rumbleR = 0;
	public Timer rumbleTimer;
	
	public XboxController(int port) {
		super(port);
		a = new JoystickButton(this, 1);
		b = new JoystickButton(this, 2);
		x = new JoystickButton(this, 3);
		y = new JoystickButton(this, 4);
		start = new JoystickButton(this, 8);
		select = new JoystickButton(this, 7);
		leftBumper = new JoystickButton(this, 5);
		rightBumper = new JoystickButton(this, 6);
		leftJoyButton = new JoystickButton(this, 9);
		rightJoyButton = new JoystickButton(this, 10);
		dpadUp = new InternalButton();
		dpadRight = new InternalButton();
		dpadDown = new InternalButton();
		dpadLeft = new InternalButton();
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
	 * Gives the current Direction of the DPad.
	 * @return The Direction of the DPad. Returns null if the DPad is not pressed.
	 */
	public Direction getDPad() {
		return Direction.toDirection(getPOV(0));
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
		rumbleTimer = new Timer(time, false, new TimerUser() {
			public void timer() {
				rumble(0, 0);
			}
			public void timerStop() {
				rumbleTimer = null;
			}
		}).start();
	}
	
	/**
	 * Used to update the DPad button values. Use this before trying to read any DPad 
	 * buttons. This isn't needed for getDPad().
	 */
	public void updateDPad() {
		Direction d = getDPad();
    	dpadUp.setPressed(d.isUp());
    	dpadRight.setPressed(d.isRight());
    	dpadDown.setPressed(d.isDown());
    	dpadLeft.setPressed(d.isLeft());
	}
	
	public enum Direction {
		UP(0),
		UP_RIGHT(45),
		RIGHT(90),
		DOWN_RIGHT(135),
		DOWN(180),
		DOWN_LEFT(225),
		LEFT(270),
		UP_LEFT(315),
		NONE(-1);
		
		public static Direction[] allDirections = new Direction[]{Direction.UP, Direction.UP_RIGHT, Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN, Direction.DOWN_LEFT, Direction.LEFT, Direction.UP_LEFT, Direction.NONE};
		public int angle;
		
		Direction(int angle) {
			this.angle = angle;
		}			
		
		public boolean isUp() {
			if (this == Direction.UP_LEFT || this == Direction.UP || this == Direction.UP_RIGHT) return true;
			return false;
		}
		
		public boolean isRight() {
			if (this == Direction.UP_RIGHT || this == Direction.RIGHT || this == Direction.DOWN_RIGHT) return true;
			return false;
		}
		
		public boolean isDown() {
			if (this == Direction.DOWN_LEFT || this == Direction.DOWN || this == Direction.DOWN_RIGHT) return true;
			return false;
		}
		
		public boolean isLeft() {
			if (this == Direction.UP_LEFT || this == Direction.LEFT || this == Direction.DOWN_LEFT) return true;
			return false;
		}
		
		public static Direction toDirection(int angle) {
			for (Direction d : allDirections) {
				if (d.angle == angle) {
					return d;
				}
			}
			return null;
		}
	}
}
