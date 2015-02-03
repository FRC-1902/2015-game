package org.usfirst.frc.team1902.robot.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
 
public class AutonomousCommand extends Command {

	public List<String[]> commands = new ArrayList<>();
	boolean initialized = false;

	public AutonomousCommand() {
		requires(Robot.autonomous);
	}
	
	protected void initialize() {		
	}

	protected void execute() {
		if (initialized) {
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
						Robot.drive.gyroTurn(Double.parseDouble(s[1]));
					} else if (s[0].equals("intakeMotor")) {
						Robot.intake.setMotors(Boolean.parseBoolean(s[1]));
					} else if (s[0].equals("reverseIntake")) {
						Robot.intake.setReversed(Boolean.parseBoolean(s[1]));
					} else if (s[0].equals("rotateIntake")) {
						Robot.intake.setRotated(Boolean.parseBoolean(s[1]));
					} else if (s[0].equals("intakeArms")) {
						Robot.intake.setArms(Boolean.parseBoolean(s[1]));
					} else if (s[0].equals("canGrabber")) {
						Robot.binGrabber.setGrabber(Boolean.parseBoolean(s[1]));
					} else if (s[0].equals("lift")) {
						Robot.lift.lift(Double.parseDouble(s[1]));   		    		
					} else if (s[0].equals("pushTote")) {
						Robot.lift.pushTote();
					} else if (s[0].equals("adjustToTote")) {
						while (!Robot.drive.adjustToTote());
					}
				commands.remove(commands.get(0));				
			} else {
				Robot.autonomous.light.set(true);
			}
		} else {
			Robot.drive.leftEncoder.reset();
			Robot.drive.rightEncoder.reset();
			Robot.drive.gyro.reset();
			Robot.autonomous.light.set(true);
			Timer.delay(2);
			Robot.autonomous.light.set(false);
			commands = new ArrayList<>();
			List<String[]> mergedCommands = new ArrayList<>();
			List<String[]> dataSource = new ArrayList<>();
			if (Robot.autonomous.readData) {
				//File file = new File("default.auto");
				//System.out.println("Attempting to read Autonomous data from " + file.getName() + "...");
				//if (file.exists()) {
					//try {
						//BufferedReader br = new BufferedReader(new FileReader(file));
						String stuff = "turn:45.763898450480745]drive:2200.157933302361:2200.157933302361]turn:-134.9512468358629]drive:2852.0565802067645:2852.0565802067645]turn:130.4287926904935]drive:1955.69594071321:1955.69594071321]";
				  		//for (String s : br.readLine().split("]")) {
						for (String s : stuff.split("]")) {
							dataSource.add(s.split(":"));
						}
						//br.close();
					//} catch (FileNotFoundException e) {
						//e.printStackTrace();
					//} catch (IOException e) {
						//e.printStackTrace();
					//}
					//System.out.println("Copied Autonomous data from " + file.getName() + ".");
				//}
				System.out.println("Read pre-loaded autonomous data.");
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
						double min = 80;				
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
			initialized = true;			
			System.out.println("AutonomousCommand has finished initializing.");
		}		
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
		Robot.drive.left1.set(Robot.drive.left1.get() * -1);
		Robot.drive.right1.set(Robot.drive.right1.get() * -1);
		Robot.autonomous.light.set(false);
	}
}
