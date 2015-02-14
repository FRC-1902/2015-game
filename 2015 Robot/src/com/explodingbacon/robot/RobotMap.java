package com.explodingbacon.robot;

public class RobotMap {
	//Drive Train Values
	public static int leftDriveVictor1 = 0; //ID 0
	public static int leftDriveVictor2 = 1; //ID 1
	public static int rightDriveVictor1 = 2; //ID 3
	public static int rightDriveVictor2 = 3; //ID 4
	
	public static int leftEncoderA = 0; //ID 11
	public static int leftEncoderB = 1; //ID 12
	public static int rightEncoderA = 2; //ID 13
	public static int rightEncoderB = 3; //ID 14
	public static int gyro = 0; //ID 21
	
	//Intake Values
	public static int leftIntakeTalon = 8; //ID 9
	public static int rightIntakeTalon = 9; //ID 10
	public static int leftArmPivotTalon = 4; //ID 4
	public static int rightArmPivotTalon = 5; //ID 5
	public static int rollerRelay = 0; //ID 8
	
	public static int chuteTouchSensor = 14; //ID 31
	public static int leftArmPivotPot = 1; //ID 22
	public static int rightArmPivotPot = 2; //ID 23
	
	//Top of Lift
	public static int drawerSlidesTalonSRX = 0x00; //ID 26
	public static int drawerSlidesEncoderA = 8; //ID 27
	public static int drawerSlidesEncoderB = 9; //ID 28
	public static int drawerSlidesOutLimit = 12; //ID 29
	public static int drawerSlidesInLimit = 13; //ID 30
	public static int binGrabberSolenoid = 0; //ID 24
	
	//Lift Values
	public static int liftVictor1 = 6; //ID 6
	public static int liftVictor2 = 7; //ID 7
	public static int liftEncoderA = 6; //ID 17
	public static int liftEncoderB = 7; //ID 18
	public static int liftTopLimit = 10; //ID 19
	public static int liftBottomLimit = 11; //ID 20
	
	//TODO: Remove
	public static int autonomousLight = 6;  //Legacy
}
