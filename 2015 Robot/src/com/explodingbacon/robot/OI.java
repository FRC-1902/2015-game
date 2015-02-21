package com.explodingbacon.robot;

import com.explodingbacon.robot.Robot.State;
import com.explodingbacon.robot.commands.BinGrabberToggleCommand;
import com.explodingbacon.robot.commands.ToteStackCommand;
import com.explodingbacon.robot.commands.IntakeArmToggleCommand;
import com.explodingbacon.robot.commands.IntakeToggleCommand;
import com.explodingbacon.robot.commands.RollerToggleCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class OI {
	public static Joystick left = new Joystick(0);
	public static XboxController xbox = new XboxController(1);
	
	public Button intake;
	public Button reversedIntake;
	public Button intakeArms;
	public Button binGrabber;
	public Button toggleRoller;
	public Button toteStack;
	public Button doToteStack;
	public Button liftScoring;
	
	public void init() {
		intake = xbox.leftBumper;
		reversedIntake = xbox.rightBumper;
		intakeArms = xbox.x;
		binGrabber = xbox.y;
		toggleRoller = xbox.b;
		toteStack = xbox.start;
		doToteStack = xbox.select;
		liftScoring = xbox.a;
		
		//======================================================
		
		intake.whenPressed(new IntakeToggleCommand(true));
		intake.whenReleased(new IntakeToggleCommand(false));
		
		reversedIntake.whenPressed(new IntakeToggleCommand(true, State.BACKWARDS));
		reversedIntake.whenReleased(new IntakeToggleCommand(false, State.BACKWARDS));
		
		intakeArms.whenPressed(new IntakeArmToggleCommand(true));
		intakeArms.whenReleased(new IntakeArmToggleCommand(false));		
		
		binGrabber.whenPressed(new BinGrabberToggleCommand());		

		toggleRoller.whenPressed(new RollerToggleCommand());
		
		toteStack.whileHeld(new ToteStackCommand());
	}
	
	public OI() {
		init();
	}
}