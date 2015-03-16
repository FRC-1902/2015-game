package com.explodingbacon.robot.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.explodingbacon.robot.Robot;
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
							Robot.drive.inchDrive(left, right, Double.parseDouble(next[1]), Double.parseDouble(next[2]));
						} else {
							Robot.drive.inchDrive(left, right, 0, 0);
						}
					}
				} else if (s[0].equals("turn")) {
					Robot.drive.gyroTurn(Double.parseDouble(s[1]), false);
				} else if (s[0].equals("intake")) {
					Robot.intake.setMotors(Double.parseDouble(s[1]));
				} else if (s[0].equals("intakeI")) {
					Robot.intake.leftIntake.set(Double.parseDouble(s[1]));
					Robot.intake.rightIntake.set(Double.parseDouble(s[1]));
				} else if (s[0].equals("roller")) {
					Robot.intake.setRoller(Boolean.parseBoolean(s[1]));	
				} else if (s[0].equals("intakeArms")) {		
					Robot.intake.arms.set(Boolean.parseBoolean(s[1]));
				} else if (s[0].equals("lift")) {
					Robot.lift.target = Robot.lift.stringToTarget(s[1]);
				} else if (s[0].equals("toteTongue")) {
					Robot.lift.liftPiston.set(Boolean.parseBoolean(s[1]));
				} else if (s[0].equals("liftWait")) {
					Robot.lift.setTargetAndWait(Robot.lift.stringToTarget(s[1]));
				} else if (s[0].equals("drawerSlides")) {
					Robot.drawerSlides.setDrawerSlides(Double.parseDouble(s[1]));
				} else if (s[0].equals("stackTote")) {
					Robot.lift.stackTote();
				} else if (s[0].equals("wait")) {
					Timer.delay(Double.parseDouble(s[1]));
				} else {
					System.out.println("Unknown autonomous command: " + s[0]);
				}
				commands.remove(commands.get(0));
			}
		} else {
			actuallyInit();	
		}
	}

	public void actuallyInit() {
		Robot.drive.leftEncoder.reset();
		Robot.drive.rightEncoder.reset();
		Robot.drive.gyro.reset();
		commands = new ArrayList<>();
		List<String[]> mergedCommands = new ArrayList<>();
		List<String[]> dataSource = new ArrayList<>();
		if (Robot.autonomous.data.isEmpty()) {
			File file = (File) Robot.chooser.getSelected();
			if (file != null && file.exists()) {
				//System.out.println("Attempting to read Autonomous data from '" + file.getName() + "'...");
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = br.readLine();
					if (line != null) {
						for (String s : line.split("]")) {
							dataSource.add(s.split(":"));
						}
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
					double min = 1;
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
						System.out.println("Merged " + merges + " drive command(s) into " + left + " left and " + right + " right.");
					}
				}
				commands.add(s);
			}
		}
		Robot.angle = 0;
		initialized = true;
		didDelay = false;
		Robot.intake.control = false;
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
		Robot.intake.control = true;
		Robot.drive.tankDrive(0, 0);
	}
}
