package org.usfirst.frc.team1902.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1902.robot.Robot;
import org.usfirst.frc.team1902.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutonomousSubsystem extends Subsystem {

	public List<String[]> data = new ArrayList<>();
	public boolean recording = false;
	public Solenoid light = new Solenoid(RobotMap.autonomousLight);
	// public Timer timer = null;
	// public NetworkTable table = null;

	public void enable() {
		recording = true;
		data = new ArrayList<>();
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		Robot.drive.gyro.reset();
		Timer.delay(0.25);
		if (Math.abs(Robot.drive.leftEncoder.getDistance()) <= 15) {
			light.set(true);
		}
		System.out.println("Recording teleop actions for autonomous!");
	}

	public void disable() {
		// TODO write the autonomous to a file on the roboRIO
		recording = false;
		light.set(false);
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		System.out.println("No longer recording teleop actions.");
	}

	public void add(String[] add) {
		if (recording) {
			data.add(add);
		}
	}

	public void initDefaultCommand() {        
	}
}

