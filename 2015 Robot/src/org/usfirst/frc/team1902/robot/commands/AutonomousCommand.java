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
						Robot.drive.gyroTurn(Double.parseDouble(s[1]), false);
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
						String stuff = "turn:0.0]drive:2363.132595028462:2363.132595028462]turn:90]drive:3748.417219700319:3748.417219700319]turn:-0.0]drive:1711.2339481240588:1711.2339481240588]turn:-90]drive:7496.834439400638:7496.834439400638]turn:-180]drive:1548.2592863979578:1548.2592863979578]turn:-270]drive:3666.9298888372687:3666.9298888372687]turn:0]drive:5296.676506098277:5296.676506098277]turn:-180]drive:7659.809101126739:7659.809101126739]";
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
			Robot.angle = 0;
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
