package com.explodingbacon.robot.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.explodingbacon.robot.Robot;
import com.explodingbacon.robot.Robot.State;
import com.explodingbacon.robot.subsystems.IntakeArmsSubsystem.Arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousCommand extends Command {

	public List<String[]> commands = new ArrayList<>();
	boolean initialized = false;
	boolean didDelay = false;

	public AutonomousCommand() {
		requires(Robot.autonomous);
	}

	protected void initialize() {
	}

	protected void execute() {
		if (initialized) {
			if (!didDelay) {
				Timer.delay(SmartDashboard.getNumber("Delay", 0));
				didDelay = true;
			}
			if (!commands.isEmpty()) {
				String[] s = commands.get(0);
				if (s[0].equals("drive")) {
					double left = Double.parseDouble(s[1]);
					double right = Double.parseDouble(s[2]);
					if (Math.abs(left) != 0 && Math.abs(right) != 0) {
						String[] next = null;
						if (commands.size() > 1) {
							next = commands.get(1);
						}
						if (next != null && next[0].equals("drive")) {
							Robot.drive.encoderDrive(left, right, Double.parseDouble(next[1]), Double.parseDouble(next[2]));
						} else {
							Robot.drive.encoderDrive(left, right, 0, 0);
						}
					}
				} else if (s[0].equals("turn")) {
					Robot.drive.gyroTurn(Double.parseDouble(s[1]), false);
				} else if (s[0].equals("intakeMotor")) {
					Robot.intake.setMotors(Double.parseDouble(s[1]));
				} else if (s[0].equals("roller")) {
					Robot.intake.setRoller(Boolean.parseBoolean(s[1]));	
				} else if (s[0].equals("intakeArm")) {
					Robot.intakeArms.setArm(Arm.valueOf(s[1]), State.valueOf(s[2]));
				} else if (s[0].equals("binGrabber")) {
					Robot.binGrabber.setGrabber(Boolean.parseBoolean(s[1]));
				} else if (s[0].equals("lift")) {
					Robot.lift.setRaw(Double.parseDouble(s[1]));
				} else if (s[0].equals("drawerSlides")) {
					Robot.drawerSlides.setDrawerSlides(Double.parseDouble(s[1]));
				}
				commands.remove(commands.get(0));
			} else {
				Robot.autonomous.light.set(true);
			}
		} else {
			actuallyInit();	
		}
	}

	public void actuallyInit() {
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		Robot.drive.gyro.reset();
		Robot.autonomous.light.set(true);
		Timer.delay(2);
		Robot.autonomous.light.set(false);
		commands = new ArrayList<>();
		List<String[]> mergedCommands = new ArrayList<>();
		List<String[]> dataSource = new ArrayList<>();
		if (Robot.autonomous.data.isEmpty()) {
			File file = (File) Robot.chooser.getSelected();
			if (file != null && file.exists()) {
				System.out.println("Attempting to read Autonomous data from '" + file.getName() + "'...");
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					for (String s : br.readLine().split("]")) {
						dataSource.add(s.split(":"));
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Read Autonomous data from '" + file.getName() + "'!");
			} else {
				System.out.println("No default autonomous found! Doing nothing...");
			}
		} else {
			dataSource = new ArrayList<>(Robot.autonomous.data);
			System.out.println("Got Autonomous data from recorded teleop actions.");
		}
		for (String[] s : dataSource) {
			if (!mergedCommands.contains(s)) {
				if (s[0].equals("drive")) {
					double left = Double.parseDouble(s[1]);
					double right = Double.parseDouble(s[2]);
					int merges = 0;
					double min = 200;
					if (Math.abs(left) < min && Math.abs(right) < min) {
						boolean stopMerge = false;
						for (String[] s2 : commands) {
							if ((Math.abs(left) < min && Math.abs(right) < min) || stopMerge) {
								if (s2[0].equals("drive")) {
									double mergeLeft = Double.parseDouble(s2[1]);
									double mergeRight = Double.parseDouble(s2[2]);
									if (mergeLeft != 0 && mergeRight != 0) {
										left += Double.parseDouble(s2[1]);
										right += Double.parseDouble(s2[2]);
										mergedCommands.add(s2);
										merges++;
									} else {
										stopMerge = true;
									}
								}
							} else {
								break;
							}
						}
					}
					if (merges > 0) {
						s[1] = left + "";
						s[2] = right + "";
						System.out.println("Merged " + merges + " command(s) into " + left + " left and " + right + " right.");
					}
				}
				commands.add(s);
			}
		}
		Robot.angle = 0;
		initialized = true;
		didDelay = false;
		System.out.println("Autonomous has finished initializing.");
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		wrapUp();
	}

	protected void interrupted() {
		wrapUp();
	}

	public void wrapUp() {
		initialized = false;
		Robot.drive.tankDrive(0, 0);
		Robot.autonomous.light.set(false);
	}
}
