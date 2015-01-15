package org.usfirst.frc.team1902.robot.commands;

import java.util.ArrayList;
import java.util.List;
import org.usfirst.frc.team1902.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommand extends Command {

	public List<String[]> commands = new ArrayList<>();
	
    public AutonomousCommand(String auto) {
    	requires(Robot.drive);
    	requires(Robot.intake);
    	requires(Robot.canGrabber);
    	requires(Robot.recordedAutonomous);
    	for (String s : auto.split("]")) {
    		commands.add(s.split("|"));
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!commands.isEmpty()) {
    		String[] s = commands.get(0);
    		if (s[0].equals("drive")) {
    			Robot.drive.tankDrive(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
    		} else if (s[0].equals("intakeMotor")) {
    			Robot.intake.setMotors(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("intakeArms")) {
    			Robot.intake.setArms(Boolean.parseBoolean(s[1]));
    		} else if (s[0].equals("canGrabber")) {
    			Robot.canGrabber.setGrabber(Boolean.parseBoolean(s[1]));
    		}
    		commands.remove(commands.get(0));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
