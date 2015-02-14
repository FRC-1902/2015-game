package com.explodingbacon.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
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
	
	public enum Direction {
		NORTH(0),
		NORTH_EAST(45),
		EAST(90),
		SOUTH_EAST(135),
		SOUTH(180),
		SOUTH_WEST(225),
		WEST(270),
		NORTH_WEST(315);
		
		public static Direction[] allDirections = new Direction[]{Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
		public int angle;
		
		Direction(int angle) {
			this.angle = angle;
		}			
		
		public boolean isNorth() {
			if (this == Direction.NORTH_WEST || this == Direction.NORTH || this == Direction.NORTH_EAST) return true;
			return false;
		}
		
		public boolean isEast() {
			if (this == Direction.NORTH_EAST || this == Direction.EAST || this == Direction.SOUTH_EAST) return true;
			return false;
		}
		
		public boolean isSouth() {
			if (this == Direction.SOUTH_WEST || this == Direction.SOUTH || this == Direction.SOUTH_EAST) return true;
			return false;
		}
		
		public boolean isWest() {
			if (this == Direction.NORTH_WEST || this == Direction.WEST || this == Direction.SOUTH_WEST) return true;
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
