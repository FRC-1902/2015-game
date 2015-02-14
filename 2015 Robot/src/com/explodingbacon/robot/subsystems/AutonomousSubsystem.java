package com.explodingbacon.robot.subsystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.RobotMap;
import com.explodingbacon.robot.Timer;
import com.explodingbacon.robot.TimerUser;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutonomousSubsystem extends Subsystem {

	public List<String[]> data = new ArrayList<>();
	public boolean recording = false;
	public Solenoid light = new Solenoid(RobotMap.autonomousLight);
	public Timer timer;

	public void enable() {
		/*
		recording = true;
		data = new ArrayList<>();
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		Robot.drive.gyro.reset();
		edu.wpi.first.wpilibj.Timer.delay(0.25);
		if (Math.abs(Robot.drive.leftEncoder.getDistance()) <= 15) {
			light.set(true);
		}
		timer = new Timer(1.0, new TimerUser() {
			public void timer() {
				light.set(!light.get());
			}
			public void timerStop() {
				timer = null;
			}
		});
		System.out.println("Recording teleop actions for autonomous!");
		*/
	}

	public void disable() {
		/*
		File usbDir = new File("/u/.auto/");
		if (usbDir.exists()) {
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(new File("/u/.auto/recordedAuto.auto")));

				String write = "";
				for (String[] array : data) {
					String command = "";
					for (String s : array) {
						command = command + s + (s == array[array.length - 1] ? "]" : ":");
					}
					 write = write + command;
				}
				bw.write(write);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		recording = false;
		light.set(false);
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		System.out.println("No longer recording teleop actions.");
		*/
	}

	public void add(String[] add) {
		if (recording) {
			data.add(add);
		}
	}

	public void initDefaultCommand() {        
	}
}

